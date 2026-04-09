package cn.northpark.controller;

import cn.northpark.annotation.CheckLogin;
import cn.northpark.result.Result;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.BaZiService;
import cn.northpark.threadLocal.RequestHolder;
import cn.northpark.vo.UserVO;
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
 * 需要 token 登录授权（@CheckLogin），每用户每天免费调用一次。
 * <p>
 * 后续扩展方向：
 *   - 付费解锁完整报告
 *   - MBTI 分析结果融合
 *   - 喂给大模型生成综合分析报告
 */
@Slf4j
@RestController
@RequestMapping("/api/bazi")
public class BaZiController {

    @Autowired
    private BaZiService baZiService;

    /**
     * 八字排盘 + 运势查看
     *
     * @param year    出生年（必须）
     * @param month   出生月（必须）
     * @param day     出生日（必须）
     * @param hour    出生时，0-23（必须）
     * @param minute  出生分，0-59（可选，默认0）
     * @param gender  性别（必须）：male / female
     * @param name    姓名/备注（可选）
     */
    @PostMapping("/reading")
    @CheckLogin
    public Result<?> reading(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int day,
            @RequestParam int hour,
            @RequestParam(defaultValue = "0") int minute,
            @RequestParam String gender,
            @RequestParam(required = false) String name,
            HttpServletRequest request) {

        // 参数校验
        if (month < 1 || month > 12 || day < 1 || day > 31
                || hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return ResultGenerator.genErrorResult(400, "日期或时间参数不合法");
        }
        if (StringUtils.isBlank(gender)) {
            return ResultGenerator.genErrorResult(400, "性别参数不能为空");
        }
        boolean isMale = "male".equalsIgnoreCase(gender.trim()) || "1".equals(gender.trim());

        UserVO user = RequestHolder.getUserInfo(request);
        // LoginInterceptor 已保证 user 不为 null，此处防御性判断
        if (user == null) {
            return ResultGenerator.genErrorResult(401, "请先登录");
        }

        return baZiService.fullReading(year, month, day, hour, minute, isMale, name, user, request);
    }
}
