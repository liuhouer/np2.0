package cn.northpark.controller;

import cn.northpark.mapper.BaZiRecordMapper;
import cn.northpark.model.BaZiRecord;
import cn.northpark.result.Result;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.BaZiAiService;
import cn.northpark.service.BaZiService;
import cn.northpark.utils.EnvCfgUtil;
import cn.northpark.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 八字排盘 & 运势接口
 */
@Slf4j
@Controller
@RequestMapping("/api/bazi")
public class BaZiController {

    private static final String BAZI_TOKEN = EnvCfgUtil.getValByCfgName("BAZI_TOKEN");

    // 小程序 AppID / AppSecret（原有，保留）
    private static final String AppID     = EnvCfgUtil.getValByCfgName("AppID");
    private static final String AppSecret = EnvCfgUtil.getValByCfgName("AppSecret");

    // 公众号 AppID / AppSecret
    private static final String GZHAppID     = EnvCfgUtil.getValByCfgName("GZHAppID");
    private static final String GZHAppSecret = EnvCfgUtil.getValByCfgName("GZHAppSecret");

    @Autowired
    private BaZiService baZiService;

    @Autowired
    private BaZiAiService baZiAiService;

    @Autowired
    private BaZiRecordMapper baZiRecordMapper;

    // ─────────────────────────────────────────────────────────────────────────
    // 页面入口：公众号菜单配置跳转此 URL
    //
    // 【当前方案：UUID 方案（方案一）】
    //   个人订阅号不支持网页授权，无法通过 OAuth2 获取 openId。
    //   改为直接渲染页面，openId 由前端 JS 生成 UUID 并存入 localStorage。
    //   后端此处不再做任何微信交互，直接返回 bazi.jsp。
    //
    // 【保留备用：OAuth2 方案（方案三，升级服务号后可启用）】
    //   原有的微信授权重定向逻辑已注释保留在下方，
    //   升级为服务号后取消注释、删除直接 return 即可切换。
    // ─────────────────────────────────────────────────────────────────────────
    @GetMapping("/index")
    public String baziPage(
            @RequestParam(required = false) String code,
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap map) throws IOException {

        // ── 方案一（当前启用）：直接渲染页面，openId 由前端 UUID 生成 ──────────
        // 不传任何 openId 给模板，前端 JS 自行生成并管理
        return "bazi";

        // ── 方案三（备用，升级服务号后启用，删除上方 return 并取消以下注释）──────
        /*
        // 先检查 session 中是否已有 openId（避免重复换取）
        String openId = (String) request.getSession().getAttribute("baziOpenId");

        if (StringUtils.isBlank(openId)) {
            if (StringUtils.isBlank(code)) {
                // 没有 code，发起微信网页授权（snsapi_base 静默授权，无需用户同意）
                String callbackUrl = "https://northpark.cn/api/bazi/index";
                String encodedUrl = java.net.URLEncoder.encode(callbackUrl, "UTF-8");
                String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize"
                        + "?appid=" + GZHAppID
                        + "&redirect_uri=" + encodedUrl
                        + "&response_type=code"
                        + "&scope=snsapi_base"
                        + "&state=bazi"
                        + "#wechat_redirect";
                response.sendRedirect(authUrl);
                return null;
            }

            // 用 code 换取 openId
            openId = fetchGzhOpenId(code);
            if (StringUtils.isNotBlank(openId)) {
                request.getSession().setAttribute("baziOpenId", openId);
                log.info("公众号八字页面获取openId成功: {}", openId);
            } else {
                log.error("公众号八字页面获取openId失败, code={}", code);
            }
        }

        map.addAttribute("openId", openId != null ? openId : "");
        return "bazi";
        */
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 原有：小程序通过 code 换取 openid（保留不动）
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/getOpenId")
    @ResponseBody
    public Result<?> getOpenId(@RequestParam String code, HttpServletRequest request) {

        if (StringUtils.isBlank(code)) {
            return ResultGenerator.genErrorResult(400, "code 不能为空");
        }

        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?"
                    + "appid=" + AppID
                    + "&secret=" + AppSecret
                    + "&js_code=" + code
                    + "&grant_type=authorization_code";

            RestTemplate restTemplate = new RestTemplate();
            String resp = restTemplate.getForObject(url, String.class);
            JSONObject json = JSON.parseObject(resp);

            if (json.containsKey("openid")) {
                String openid = json.getString("openid");
                String sessionKey = json.getString("session_key");
                RedisUtil.getInstance().set("openid_:" + openid, sessionKey);
                log.info("小程序换取openid成功: {}", openid);

                Map<String, Object> data = new HashMap<>();
                data.put("openid", openid);
                return ResultGenerator.genSuccessResult(data);
            } else {
                String errmsg = json.getString("errmsg");
                log.error("小程序换取openid失败: {}", errmsg);
                return ResultGenerator.genErrorResult(500, "获取openid失败: " + errmsg);
            }
        } catch (Exception e) {
            log.error("调用微信jscode2session接口异常", e);
            return ResultGenerator.genErrorResult(500, "服务器内部错误");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 八字排盘主接口（小程序 & 公众号 H5 共用）
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/reading")
    @ResponseBody
    public Result<?> reading(
            @RequestParam("open_id") String openId,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int day,
            @RequestParam int hour,
            @RequestParam(defaultValue = "0") int minute,
            @RequestParam String gender,
            @RequestParam(required = false) String name,
            HttpServletRequest request) {

        String clientIp = getClientIp(request);

        // 1. Token 校验
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || !token.equals(BAZI_TOKEN)) {
            log.warn("[BaziAttack] token 无效, ip={}", clientIp);
            return ResultGenerator.genErrorResult(401, "token 无效或缺失");
        }

        // 2. IP 频率限制：1 分钟内最多 10 次请求
        String rateKey = "bazi:rate:" + clientIp;
        String rateStr = RedisUtil.getInstance().get(rateKey);
        int rateCount = rateStr == null ? 0 : Integer.parseInt(rateStr);
        if (rateCount >= 10) {
            log.warn("[BaziAttack] IP 请求过于频繁, ip={}", clientIp);
            return ResultGenerator.genErrorResult(429, "请求过于频繁，请稍后再试");
        }
        RedisUtil.getInstance().set(rateKey, String.valueOf(rateCount + 1), 60);

        // 3. openId 校验：只允许字母、数字、下划线和连字符，长度限制 64
        if (StringUtils.isBlank(openId)) {
            return ResultGenerator.genErrorResult(400, "open_id 不能为空");
        }
        if (!openId.matches("^[a-zA-Z0-9_\\-]{1,64}$")) {
            log.warn("[BaziAttack] 非法 open_id 被拦截, ip={}, open_id={}", clientIp, openId);
            return ResultGenerator.genErrorResult(400, "open_id 格式不合法");
        }

        // 4. name 校验：只允许中文、英文、数字、空格，长度 0-20
        // 拦截 SQL 注入常见字符：' " ; -- /* */ < > ( ) = | & $ % @ # ! * ? ` ~ \
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 20) {
                return ResultGenerator.genErrorResult(400, "姓名长度不能超过20字");
            }
            // 检测危险字符（SQL 注入、XSS 常见 payload）
            if (containsDangerousChars(name)) {
                log.warn("[BaziAttack] 非法 name 被拦截, ip={}, name={}", clientIp, name);
                return ResultGenerator.genErrorResult(400, "姓名包含非法字符");
            }
            // 只允许中文、英文、数字、空格
            if (!name.matches("^[\\u4e00-\\u9fa5a-zA-Z0-9\\s]{1,20}$")) {
                log.warn("[BaziAttack] 非法 name 被拦截, ip={}, name={}", clientIp, name);
                return ResultGenerator.genErrorResult(400, "姓名格式不合法");
            }
        }

        // 5. gender 严格校验
        if (StringUtils.isBlank(gender)) {
            return ResultGenerator.genErrorResult(400, "性别参数不能为空");
        }
        String g = gender.trim().toLowerCase();
        if (!("male".equals(g) || "female".equals(g) || "1".equals(g) || "0".equals(g))) {
            log.warn("[BaziAttack] 非法 gender 被拦截, ip={}, gender={}", clientIp, gender);
            return ResultGenerator.genErrorResult(400, "性别参数不合法");
        }
        boolean isMale = "male".equals(g) || "1".equals(g);

        // 6. 日期范围校验
        if (month < 1 || month > 12 || day < 1 || day > 31
                || hour < 0 || hour > 23 || minute < 0 || minute > 59
                || year < 1900 || year > 2100) {
            log.warn("[BaziAttack] 非法日期参数, ip={}, year={}, month={}, day={}, hour={}, minute={}",
                    clientIp, year, month, day, hour, minute);
            return ResultGenerator.genErrorResult(400, "日期或时间参数不合法");
        }

        return baZiService.fullReading(year, month, day, hour, minute, isMale, name, openId, request);
    }

    /**
     * 检测字符串中是否包含危险字符（SQL 注入、XSS 常见 payload）
     */
    private boolean containsDangerousChars(String input) {
        if (input == null) return false;
        String lower = input.toLowerCase();
        String[] dangerPatterns = {
            "'", "\"", ";", "--", "/*", "*/", "<", ">", "(", ")", "=", "|",
            "union", "select", "insert", "update", "delete", "drop", "exec",
            "script", "javascript:", "onerror", "onload", "alert(", "eval("
        };
        for (String pattern : dangerPatterns) {
            if (lower.contains(pattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取客户端真实 IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // AI 命理解读接口
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * AI 深度解读八字排盘结果
     *
     * @param recordId 排盘记录ID
     */
    @PostMapping("/ai-interpret")
    @ResponseBody
    public Result<?> aiInterpret(@RequestParam Long recordId,
                                  HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || !token.equals(BAZI_TOKEN)) {
            return ResultGenerator.genErrorResult(401, "token 无效或缺失");
        }
        if (recordId == null || recordId <= 0) {
            return ResultGenerator.genErrorResult(400, "recordId 参数不合法");
        }

        return baZiAiService.aiInterpret(recordId);
    }

    /**
     * AI 针对具体问题给出命理建议
     *
     * @param recordId 排盘记录ID
     * @param question 用户问题，如"今年适合跳槽吗？"
     */
    @PostMapping("/ai-advice")
    @ResponseBody
    public Result<?> aiAdvice(@RequestParam Long recordId,
                               @RequestParam String question,
                               HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || !token.equals(BAZI_TOKEN)) {
            return ResultGenerator.genErrorResult(401, "token 无效或缺失");
        }
        if (recordId == null || recordId <= 0) {
            return ResultGenerator.genErrorResult(400, "recordId 参数不合法");
        }

        return baZiAiService.aiAdvice(recordId, question);
    }

    /**
     * 查询用户最近一次排盘记录（用于页面刷新后自动恢复）
     */
    @GetMapping("/latest")
    @ResponseBody
    public Result<?> latest(@RequestParam("open_id") String openId,
                            HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || !token.equals(BAZI_TOKEN)) {
            return ResultGenerator.genErrorResult(401, "token 无效或缺失");
        }
        if (StringUtils.isBlank(openId) || !openId.matches("^[a-zA-Z0-9_\\-]{1,64}$")) {
            return ResultGenerator.genErrorResult(400, "open_id 格式不合法");
        }

        BaZiRecord record = baZiRecordMapper.selectLatestByOpenId(openId);
        if (record == null) {
            return ResultGenerator.genSuccessResult(null);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("recordId", record.getId());
        data.put("name", record.getName());
        data.put("gender", record.getGender());
        data.put("birthYear", record.getBirthYear());
        data.put("birthMonth", record.getBirthMonth());
        data.put("birthDay", record.getBirthDay());
        data.put("birthHour", record.getBirthHour());
        data.put("birthMinute", record.getBirthMinute());
        data.put("panResult", record.getPanResult());
        data.put("yunResult", record.getYunResult());
        data.put("aiInterpret", record.getAiInterpret());
        data.put("aiAdvice", record.getAiAdvice());

        try {
            if (record.getPanVo() != null) {
                data.put("panVO", JSON.parseObject(record.getPanVo()));
            }
            if (record.getYunVo() != null) {
                data.put("yunVO", JSON.parseObject(record.getYunVo()));
            }
        } catch (Exception e) {
            log.warn("解析历史记录 VO JSON 失败, recordId={}", record.getId());
        }

        return ResultGenerator.genSuccessResult(data);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 私有：公众号 OAuth2 code 换取 openId
    // ─────────────────────────────────────────────────────────────────────────
    private String fetchGzhOpenId(String code) {
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                    + "appid=" + GZHAppID
                    + "&secret=" + GZHAppSecret
                    + "&code=" + code
                    + "&grant_type=authorization_code";

            RestTemplate restTemplate = new RestTemplate();
            String resp = restTemplate.getForObject(url, String.class);
            JSONObject json = JSON.parseObject(resp);

            if (json != null && json.containsKey("openid")) {
                return json.getString("openid");
            }
            log.error("公众号换取openId失败: {}", resp);
        } catch (Exception e) {
            log.error("公众号换取openId异常", e);
        }
        return null;
    }
}
