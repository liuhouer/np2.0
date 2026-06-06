package cn.northpark.controller;

import cn.northpark.result.Result;
import cn.northpark.result.ResultGenerator;
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

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || !token.equals(BAZI_TOKEN)) {
            return ResultGenerator.genErrorResult(401, "token 无效或缺失");
        }
        if (StringUtils.isBlank(openId)) {
            return ResultGenerator.genErrorResult(400, "open_id 不能为空");
        }
        // 防止恶意输入：openId 只允许字母、数字、下划线和连字符，长度限制 64
        if (!openId.matches("^[a-zA-Z0-9_\\-]{1,64}$")) {
            log.warn("非法 open_id 被拦截, ip={}, open_id={}", request.getRemoteAddr(), openId);
            return ResultGenerator.genErrorResult(400, "open_id 格式不合法");
        }
        if (month < 1 || month > 12 || day < 1 || day > 31
                || hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return ResultGenerator.genErrorResult(400, "日期或时间参数不合法");
        }
        if (StringUtils.isBlank(gender)) {
            return ResultGenerator.genErrorResult(400, "性别参数不能为空");
        }
        boolean isMale = "male".equalsIgnoreCase(gender.trim()) || "1".equals(gender.trim());

        return baZiService.fullReading(year, month, day, hour, minute, isMale, name, openId, request);
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
