package cn.northpark.service;

import cn.northpark.result.Result;

import javax.servlet.http.HttpServletRequest;

public interface BaZiService {

    /**
     * 八字排盘 + 运势，含每日免费次数控制和记录保存
     *
     * @param openId 微信用户 openId，用于每日限流和记录
     */
    Result<?> fullReading(int year, int month, int day, int hour, int minute,
                          boolean isMale, String name, String openId, HttpServletRequest request);
}
