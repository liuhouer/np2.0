package cn.northpark.controller;

import cn.northpark.result.Result;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.BaZiService;
import cn.northpark.utils.EnvCfgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private BaZiService baZiService;

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
