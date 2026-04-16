package cn.northpark.service;

import cn.northpark.result.Result;
import cn.northpark.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface BaZiService {

    /**
     * 八字排盘 + 运势，含每日免费次数控制和记录保存
     * 返回结构：{ panVO, yunVO, panText, yunText, recordId }
     */
    Result<?> fullReading(int year, int month, int day, int hour, int minute,
                          boolean isMale, String name, UserVO user, HttpServletRequest request);
}
