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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 八字排盘 & 运势接口
 * <p>
 * POST /api/bazi/reading
 * 通过 Header 中的 token 与 BAZI_TOKEN 匹配完成授权，无需系统用户登录。
 * 每个微信用户（open_id）每天限制访问 1 次。
 */
@Slf4j
@RestController
@RequestMapping("/api/bazi")
public class BaZiController {

    private static final String BAZI_TOKEN = EnvCfgUtil.getValByCfgName("BAZI_TOKEN");
    private static final String AppID = EnvCfgUtil.getValByCfgName("AppID");//AppID(小程序ID)
    private static final String AppSecret = EnvCfgUtil.getValByCfgName("AppSecret");//AppSecret(小程序密钥)

    @Autowired
    private BaZiService baZiService;

    /**
     * 【新增】小程序通过 code 换取 openid
     * <p>
     * POST /api/bazi/getOpenId
     * 前端小程序登录时调用此接口，传入 wx.login() 获取的 code
     *
     * @param code 微信小程序登录返回的 code（必须）
     * @return { "code": 200, "data": { "openid": "xxxxxx" } }
     */
    @PostMapping("/getOpenId")
    public Result<?> getOpenId(@RequestParam String code, HttpServletRequest request) {

        if (StringUtils.isBlank(code)) {
            return ResultGenerator.genErrorResult(400, "code 不能为空");
        }

        try {
            // 调用微信接口换取 openid
            String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                    "appid=" + AppID +
                    "&secret=" + AppSecret +
                    "&js_code=" + code +
                    "&grant_type=authorization_code";

            // 这里推荐使用 RestTemplate 或 WebClient 调用（下面给出 RestTemplate 示例）
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // 解析返回的 JSON
            JSONObject jsonObject = JSON.parseObject(response);

            if (jsonObject.containsKey("openid")) {
                String openid = jsonObject.getString("openid");
                // 可选：记录 session_key（建议存入 redis，后续解密手机号等会用到）
                String sessionKey = jsonObject.getString("session_key");

                String redisKey = "openid_:" + openid;
                RedisUtil.getInstance().set(redisKey, sessionKey);

                log.info("微信换取openid成功: {}", openid);

                // 返回 openid（可根据实际需求同时返回 session_key）
                Map<String, Object> data = new HashMap<>();
                data.put("openid", openid);

                return ResultGenerator.genSuccessResult(data);
            } else {
                String errcode = jsonObject.getString("errcode");
                String errmsg = jsonObject.getString("errmsg");
                log.error("微信换取openid失败: errcode={}, errmsg={}", errcode, errmsg);
                return ResultGenerator.genErrorResult(500, "获取openid失败: " + errmsg);
            }

        } catch (Exception e) {
            log.error("调用微信jscode2session接口异常", e);
            return ResultGenerator.genErrorResult(500, "服务器内部错误");
        }
    }

    /**
     * 八字排盘 + 运势查看
     *
     * @param openId  微信用户 openId（必须），用于每日限流
     * @param year    出生年（必须）
     * @param month   出生月（必须）
     * @param day     出生日（必须）
     * @param hour    出生时，0-23（必须）
     * @param minute  出生分，0-59（可选，默认0）
     * @param gender  性别（必须）：male / female
     * @param name    姓名/备注（可选）
     */
    @PostMapping("/reading")
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

        // Token 校验
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || !token.equals(BAZI_TOKEN)) {
            return ResultGenerator.genErrorResult(401, "token 无效或缺失");
        }

        // open_id 校验
        if (StringUtils.isBlank(openId)) {
            return ResultGenerator.genErrorResult(400, "open_id 不能为空");
        }

        // 参数校验
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
}
