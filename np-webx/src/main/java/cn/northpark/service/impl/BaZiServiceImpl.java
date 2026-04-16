package cn.northpark.service.impl;

import cn.northpark.Xuanaobazi.BaZiEngine;
import cn.northpark.Xuanaobazi.vo.BaZiPanVO;
import cn.northpark.Xuanaobazi.vo.BaZiYunVO;
import cn.northpark.mapper.BaZiRecordMapper;
import cn.northpark.model.BaZiRecord;
import cn.northpark.result.Result;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.BaZiService;
import cn.northpark.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class BaZiServiceImpl implements BaZiService {

    /** 每用户每天免费调用次数 */
    private static final int FREE_DAILY_LIMIT = 1;

    @Autowired
    private BaZiRecordMapper baZiRecordMapper;

    @Override
    public Result<?> fullReading(int year, int month, int day, int hour, int minute,
                                 boolean isMale, String name, String openId, HttpServletRequest request) {

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
}
