package cn.northpark.service.impl;

import cn.northpark.Xuanaobazi.vo.BaZiPanVO;
import cn.northpark.Xuanaobazi.vo.BaZiYunVO;
import cn.northpark.mapper.BaZiRecordMapper;
import cn.northpark.model.BaZiRecord;
import cn.northpark.result.Result;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.BaZiAiService;
import cn.northpark.utils.EnvCfgUtil;
import cn.northpark.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BaZiAiServiceImpl implements BaZiAiService {

    private static final String GLM_KEY = EnvCfgUtil.getValByCfgName("GLM_KEY");
    private static final String GLM_MODEL = EnvCfgUtil.getValByCfgName("GLM_MODEL");

    /** GLM 开放 API 地址 */
    private static final String GLM_API_URL = "https://open.bigmodel.cn/api/paas/v4/chat/completions";

    /** 每用户每天 AI 调用总上限（ai-interpret + ai-advice 合计） */
    private static final int AI_DAILY_LIMIT = 4;

    @Autowired
    private BaZiRecordMapper baZiRecordMapper;

    @Override
    public Result<String> aiInterpret(Long recordId) {
        BaZiRecord record = baZiRecordMapper.selectByPrimaryKey(recordId);
        if (record == null) {
            return ResultGenerator.genErrorResult(404, "排盘记录不存在");
        }

        // 若已有缓存的 AI 解读，直接返回（避免重复调用 API）
        if (record.getAiInterpret() != null && !record.getAiInterpret().isEmpty()) {
            log.info("AI 解读命中缓存, recordId={}", recordId);
            return ResultGenerator.genSuccessResult(record.getAiInterpret());
        }

        // 检查每日 AI 调用限制（缓存命中不计入限制）
        if (!checkAndIncrementAiLimit(record.getOpenId())) {
            return ResultGenerator.genErrorResult(429,
                    "今日 AI 解读次数已用完（每日限" + AI_DAILY_LIMIT + "次），请明日再试");
        }

        String prompt = buildInterpretPrompt(record);
        String aiText = callGlm(prompt);
        if (aiText == null) {
            return ResultGenerator.genErrorResult(500, "AI 解读服务暂时不可用，请稍后重试");
        }

        // 保存到数据库（失败不影响返回）
        try {
            record.setAiInterpret(aiText);
            baZiRecordMapper.updateAiInterpret(record);
            log.info("AI 解读已保存, recordId={}", recordId);
        } catch (Exception e) {
            log.error("保存 AI 解读失败, recordId={}", recordId, e);
        }

        return ResultGenerator.genSuccessResult(aiText);
    }

    @Override
    public Result<String> aiAdvice(Long recordId, String question) {
        if (question == null || question.trim().isEmpty()) {
            return ResultGenerator.genErrorResult(400, "问题不能为空");
        }
        if (question.length() > 200) {
            return ResultGenerator.genErrorResult(400, "问题长度不能超过200字");
        }

        BaZiRecord record = baZiRecordMapper.selectByPrimaryKey(recordId);
        if (record == null) {
            return ResultGenerator.genErrorResult(404, "排盘记录不存在");
        }

        // 若已有缓存的 AI 建议，直接返回
        if (record.getAiAdvice() != null && !record.getAiAdvice().isEmpty()) {
            log.info("AI 建议命中缓存, recordId={}", recordId);
            return ResultGenerator.genSuccessResult(extractAnswer(record.getAiAdvice()));
        }

        // 检查每日 AI 调用限制（缓存命中不计入限制）
        if (!checkAndIncrementAiLimit(record.getOpenId())) {
            return ResultGenerator.genErrorResult(429,
                    "今日 AI 问答次数已用完（每日限" + AI_DAILY_LIMIT + "次），请明日再试");
        }

        String prompt = buildAdvicePrompt(record, question.trim());
        String aiText = callGlm(prompt);
        if (aiText == null) {
            return ResultGenerator.genErrorResult(500, "AI 建议服务暂时不可用，请稍后重试");
        }

        // 保存到数据库（失败不影响返回）
        try {
            record.setAiAdvice("【问题】" + question.trim() + "\n\n【AI回答】\n" + aiText);
            baZiRecordMapper.updateAiAdvice(record);
            log.info("AI 建议已保存, recordId={}", recordId);
        } catch (Exception e) {
            log.error("保存 AI 建议失败, recordId={}", recordId, e);
        }

        return ResultGenerator.genSuccessResult(aiText);
    }

    // ======================== Prompt 构造 ========================

    private String buildInterpretPrompt(BaZiRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一位精通子平八字命理的大师，拥有30年实战经验。\n");
        sb.append("请根据以下排盘数据，为用户提供一份详细、温暖、有指导意义的八字命理深度解读。\n");
        sb.append("解读要求：\n");
        sb.append("1. 先概述命局格局和性格特点\n");
        sb.append("2. 分析事业财运方向\n");
        sb.append("3. 分析感情婚姻趋势\n");
        sb.append("4. 分析健康需要注意的方面\n");
        sb.append("5. 给出人生发展建议\n");
        sb.append("6. 语气要温暖、积极，避免恐吓性语言\n\n");

        appendCoreData(sb, record);
        sb.append("\n请基于以上数据，输出一份完整的八字命理解读报告。");

        return sb.toString();
    }

    private String buildAdvicePrompt(BaZiRecord record, String question) {
        int currentYear = LocalDate.now().getYear();

        StringBuilder sb = new StringBuilder();
        sb.append("你是一位精通子平八字命理的大师。\n");
        sb.append("用户有一个具体的生活问题，请你结合其八字命盘和当前大运流年，给出专业、务实的建议。\n\n");

        appendCoreData(sb, record);

        sb.append("\n【当前时间】").append(currentYear).append("年\n");
        sb.append("【用户问题】").append(question).append("\n\n");
        sb.append("请基于以上命盘数据和当前流年运势，针对用户的问题给出：\n");
        sb.append("1. 命理层面的分析依据\n");
        sb.append("2. 直接回答用户问题（适合/不适合/谨慎）\n");
        sb.append("3. 具体的行动建议和注意事项\n");
        sb.append("4. 语气温暖务实，避免绝对化断言\n");

        return sb.toString();
    }

    /**
     * 提取排盘核心数据，构造 Prompt 文本
     */
    private void appendCoreData(StringBuilder sb, BaZiRecord record) {
        sb.append("【基本信息】\n");
        sb.append("- 姓名：").append(record.getName() != null ? record.getName() : "未填").append("\n");
        sb.append("- 性别：").append(record.getGender() != null && record.getGender() == 1 ? "男" : "女").append("\n");
        sb.append("- 生辰：").append(record.getBirthYear()).append("年")
          .append(record.getBirthMonth()).append("月")
          .append(record.getBirthDay()).append("日 ")
          .append(record.getBirthHour()).append("时\n\n");

        // 解析排盘 VO
        BaZiPanVO panVO = null;
        BaZiYunVO yunVO = null;
        try {
            if (record.getPanVo() != null) {
                panVO = JSON.parseObject(record.getPanVo(), BaZiPanVO.class);
            }
            if (record.getYunVo() != null) {
                yunVO = JSON.parseObject(record.getYunVo(), BaZiYunVO.class);
            }
        } catch (Exception e) {
            log.warn("解析 BaZiRecord VO JSON 失败, recordId={}", record.getId(), e);
        }

        // 四柱
        sb.append("【四柱八字】\n");
        if (panVO != null && panVO.getPillars() != null) {
            for (BaZiPanVO.PillarVO p : panVO.getPillars()) {
                sb.append("- ").append(p.getPillarName()).append("：")
                  .append(p.getGanZhi()).append("（天干").append(p.getTianGan())
                  .append("·地支").append(p.getDiZhi()).append("）");
                if (p.getTianGanShiShen() != null) {
                    sb.append(" 天干十神：").append(p.getTianGanShiShen());
                }
                sb.append("\n");
            }
        } else {
            sb.append("- 排盘数据解析异常\n");
        }
        sb.append("\n");

        // 日元旺衰 + 用神
        if (panVO != null && panVO.getRiYuanYongShen() != null) {
            BaZiPanVO.RiYuanYongShenVO ry = panVO.getRiYuanYongShen();
            sb.append("【日元旺衰】\n");
            sb.append("- 日主综合得分：").append(ry.getScore()).append("\n");
            sb.append("- 旺衰描述：").append(ry.getRiYuanDesc()).append("\n");
            sb.append("- 喜用神：").append(ry.getXiYong1());
            if (ry.getXiYong2() != null && !ry.getXiYong2().isEmpty()) {
                sb.append("、").append(ry.getXiYong2());
            }
            sb.append("\n");
            sb.append("- 忌仇神：").append(ry.getJiChou1());
            if (ry.getJiChou2() != null && !ry.getJiChou2().isEmpty()) {
                sb.append("、").append(ry.getJiChou2());
            }
            sb.append("\n\n");
        }

        // 五行力量
        if (panVO != null && panVO.getWuXing() != null) {
            BaZiPanVO.WuXingVO wx = panVO.getWuXing();
            sb.append("【五行力量分布】\n");
            sb.append("- 水：").append(wx.getShui()).append("（").append(String.format("%.1f", wx.getShuiPct())).append("%）\n");
            sb.append("- 木：").append(wx.getMu()).append("（").append(String.format("%.1f", wx.getMuPct())).append("%）\n");
            sb.append("- 火：").append(wx.getHuo()).append("（").append(String.format("%.1f", wx.getHuoPct())).append("%）\n");
            sb.append("- 土：").append(wx.getTu()).append("（").append(String.format("%.1f", wx.getTuPct())).append("%）\n");
            sb.append("- 金：").append(wx.getJin()).append("（").append(String.format("%.1f", wx.getJinPct())).append("%）\n");
            sb.append("- 己生助力量：").append(wx.getShengZhu()).append("（").append(String.format("%.1f", wx.getShengZhuPct())).append("%）\n");
            sb.append("- 克泄耗力量：").append(wx.getKeXie()).append("（").append(String.format("%.1f", wx.getKeXiePct())).append("%）\n\n");
        }

        // 神煞
        if (panVO != null && panVO.getShenSha() != null) {
            BaZiPanVO.ShenShaVO ss = panVO.getShenSha();
            sb.append("【神煞】\n");
            sb.append("- 年柱：").append(formatList(ss.getNianZhu())).append("\n");
            sb.append("- 月柱：").append(formatList(ss.getYueZhu())).append("\n");
            sb.append("- 日柱：").append(formatList(ss.getRiZhu())).append("\n");
            sb.append("- 时柱：").append(formatList(ss.getShiZhu())).append("\n\n");
        }

        // 大运
        if (panVO != null && panVO.getDaYun() != null) {
            BaZiPanVO.DaYunSummary dy = panVO.getDaYun();
            sb.append("【大运】\n");
            sb.append("- 起运方向：").append(dy.getDirection()).append("\n");
            sb.append("- 起运年龄：").append(dy.getStartAge()).append("岁（").append(dy.getStartYear()).append("年）\n");
            if (dy.getSteps() != null) {
                sb.append("- 大运列表：\n");
                for (BaZiPanVO.DaYunStepVO step : dy.getSteps()) {
                    sb.append("  第").append(step.getStep()).append("步 ")
                      .append(step.getGanZhi()).append(" ")
                      .append(step.getStartAge()).append("-").append(step.getStartAge() + 9).append("岁 ");
                    if (step.getShiShen() != null) {
                        sb.append("(").append(step.getShiShen()).append(")");
                    }
                    sb.append("\n");
                }
            }
            sb.append("\n");
        }

        // 当前流年（根据当前年份匹配）
        int currentYear = LocalDate.now().getYear();
        if (yunVO != null && yunVO.getDaYunList() != null) {
            sb.append("【").append(currentYear).append("年 流年运势】\n");
            boolean found = false;
            for (BaZiYunVO.DaYunVO dy : yunVO.getDaYunList()) {
                if (dy.getLiuNianList() == null) continue;
                for (BaZiYunVO.LiuNianVO ln : dy.getLiuNianList()) {
                    if (ln.getCalYear() == currentYear) {
                        sb.append("- 年龄：").append(ln.getAge()).append("岁\n");
                        sb.append("- 流年干支：").append(ln.getLiuNianGanZhi()).append("\n");
                        sb.append("- 流年十神：").append(ln.getLiuNianShiShen()).append("\n");
                        sb.append("- 综合运势分：").append(ln.getZongHeFen()).append("/100\n");
                        sb.append("- 运势等级：").append(ln.getScoreLevel()).append("\n");
                        if (ln.getSpecialRelations() != null && !ln.getSpecialRelations().isEmpty()) {
                            sb.append("- 特殊关系：").append(String.join("、", ln.getSpecialRelations())).append("\n");
                        }
                        if (ln.getAnalysis() != null) {
                            sb.append("- 流年分析：").append(ln.getAnalysis()).append("\n");
                        }
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (!found) {
                sb.append("- 未找到该年份的流年数据\n");
            }
            sb.append("\n");
        }
    }

    private String formatList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "无";
        }
        return String.join("、", list);
    }

    /**
     * 检查并递增用户当日 AI 调用次数
     * @return true 表示未超限，可以继续调用；false 表示已用完
     */
    private boolean checkAndIncrementAiLimit(String openId) {
        String today = LocalDate.now().toString();
        String key = "bazi:ai:daily:" + openId + ":" + today;
        String countStr = RedisUtil.getInstance().get(key);
        int count = countStr == null ? 0 : Integer.parseInt(countStr);
        if (count >= AI_DAILY_LIMIT) {
            return false;
        }
        RedisUtil.getInstance().set(key, String.valueOf(count + 1), 86400);
        return true;
    }

    // ======================== GLM API 调用 ========================

    private String callGlm(String userPrompt) {
        if (GLM_KEY == null || GLM_KEY.isEmpty() || "null".equals(GLM_KEY)) {
            log.error("GLM_KEY 未配置，无法调用 AI 服务");
            return null;
        }

        String model = (GLM_MODEL != null && !GLM_MODEL.isEmpty() && !"null".equals(GLM_MODEL))
                ? GLM_MODEL : "glm-4-flash";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(GLM_KEY);

            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", "你是一位精通子平八字命理的资深命理师，擅长用温暖、积极的语言解读命盘，给出务实的人生建议。你拒绝回答与命理无关的问题，也不提供迷信恐吓内容。");
            messages.add(systemMsg);

            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userPrompt);
            messages.add(userMsg);

            Map<String, Object> body = new HashMap<>();
            body.put("model", model);
            body.put("messages", messages);
            body.put("temperature", 0.7);
            body.put("max_tokens", 4096);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(GLM_API_URL, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String respBody = response.getBody();
                com.alibaba.fastjson.JSONObject json = JSON.parseObject(respBody);
                if (json.containsKey("choices")) {
                    com.alibaba.fastjson.JSONArray choices = json.getJSONArray("choices");
                    if (!choices.isEmpty()) {
                        com.alibaba.fastjson.JSONObject choice = choices.getJSONObject(0);
                        com.alibaba.fastjson.JSONObject msg = choice.getJSONObject("message");
                        if (msg != null && msg.containsKey("content")) {
                            return msg.getString("content");
                        }
                    }
                }
                log.warn("GLM 返回格式异常: {}", respBody);
            } else {
                log.error("GLM API 调用失败, status={}, body={}", response.getStatusCode(), response.getBody());
            }
        } catch (Exception e) {
            log.error("调用 GLM API 异常", e);
        }
        return null;
    }

    private String extractAnswer(String advice) {
        if (advice == null || advice.isEmpty()) {
            return "";
        }
        String marker = "【AI回答】\n";
        int idx = advice.indexOf(marker);
        if (idx >= 0) {
            return advice.substring(idx + marker.length());
        }
        // 兼容旧数据（纯答案文本，没有问题前缀）
        return advice;
    }
}
