package cn.northpark.service.impl;

import cn.northpark.Xuanaobazi.BaZiEngine;
import cn.northpark.Xuanaobazi.vo.BaZiPanVO;
import cn.northpark.Xuanaobazi.vo.BaZiYunVO;
import cn.northpark.mapper.BaZiRecordMapper;
import cn.northpark.model.BaZiRecord;
import cn.northpark.result.Result;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.BaZiService;
import cn.northpark.utils.EnvCfgUtil;
import cn.northpark.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class BaZiServiceImpl implements BaZiService {

    /** 每用户每天免费调用次数  八字排盘每日免费调用次数（限时免费）*/
    private static final int FREE_DAILY_LIMIT = Integer.parseInt(EnvCfgUtil.getValByCfgName("FREE_DAILY_LIMIT"));;

    @Autowired
    private BaZiRecordMapper baZiRecordMapper;

    @Override
    public Result<?> fullReading(int year, int month, int day, int hour, int minute,
                                 boolean isMale, String name, String openId, HttpServletRequest request) {

        // Service 层二次清洗：去除 name 中的可疑字符（防御纵深）
        if (StringUtils.isNotBlank(name)) {
            name = sanitizeName(name);
        }

        // openId 二次校验
        if (StringUtils.isBlank(openId) || !openId.matches("^[a-zA-Z0-9_\\-]{1,64}$")) {
            log.warn("[BaziAttack] Service 层拦截非法 openId, openId={}", openId);
            return ResultGenerator.genErrorResult(400, "参数不合法");
        }

        String redisKey = "bazi:free:" + openId + ":" + LocalDate.now();
        String countStr = RedisUtil.getInstance().get(redisKey);
        int usedCount = countStr == null ? 0 : Integer.parseInt(countStr);

        if (usedCount >= FREE_DAILY_LIMIT) {
            return ResultGenerator.genErrorResult(429, "今日免费次数已用完，请明日再试");
        }

        // 调用八字引擎，获取 VO 和文本
        BaZiEngine engine = new BaZiEngine();
        BaZiPanVO panVO;
        BaZiYunVO yunVO;
        String[] fullResult;
        try {
            panVO = engine.getPanVO(year, month, day, hour, minute, isMale, name);
            yunVO = engine.getYunVO(year, month, day, hour, minute, isMale, name);
            fullResult = engine.getFullResult(year, month, day, hour, minute, isMale, name);
        } catch (Exception e) {
            log.error("BaZiEngine error", e);
            return ResultGenerator.genErrorResult(500, "排盘计算失败，请检查输入参数");
        }

        // 扣减免费次数，写入带过期时间（到明天0点）
        int secondsUntilMidnight = (int) secondsUntilMidnight();
        RedisUtil.getInstance().set(redisKey, String.valueOf(usedCount + 1), secondsUntilMidnight);

        // 获取客户端 IP
        String ip = getClientIp(request);

        // 保存记录
        BaZiRecord record = new BaZiRecord();
        record.setOpenId(openId);
        record.setName(name);
        record.setGender(isMale ? 1 : 0);
        record.setBirthYear(year);
        record.setBirthMonth(month);
        record.setBirthDay(day);
        record.setBirthHour(hour);
        record.setBirthMinute(minute);
        record.setPanVo(JSON.toJSONString(panVO));
        record.setYunVo(JSON.toJSONString(yunVO));
        record.setPanResult(fullResult[0]);
        record.setYunResult(fullResult[1]);
        record.setIp(ip);
        record.setIsFree(1);
        try {
            baZiRecordMapper.insert(record);
        } catch (Exception e) {
            log.error("BaZiRecord insert error", e);
        }

        // 组装返回
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("panVO", panVO);
        data.put("yunVO", yunVO);
        data.put("panText", fullResult[0]);
        data.put("yunText", fullResult[1]);
        data.put("recordId", record.getId());

        return ResultGenerator.genSuccessResult(data);
    }

    private long secondsUntilMidnight() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        return java.time.Duration.between(now, midnight).getSeconds();
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级代理取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /**
     * 清洗姓名：去除 SQL 注入和 XSS 常见危险字符，只保留安全字符
     */
    private String sanitizeName(String name) {
        if (name == null) return null;
        // 第一步：去除所有非安全字符（只允许中文、英文、数字、空格）
        String cleaned = name.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9\\s]", "");
        // 第二步：去除多余空格
        cleaned = cleaned.trim().replaceAll("\\s+", " ");
        // 第三步：长度截断
        if (cleaned.length() > 20) {
            cleaned = cleaned.substring(0, 20);
        }
        return cleaned;
    }
}
