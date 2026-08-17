package cn.northpark.service;

import cn.northpark.result.Result;

/**
 * 八字 AI 解读服务
 */
public interface BaZiAiService {

    /**
     * AI 深度解读八字排盘结果
     *
     * @param recordId 排盘记录ID
     * @return AI 解读文本
     */
    Result<String> aiInterpret(Long recordId);

    /**
     * AI 针对具体问题给出命理建议
     *
     * @param recordId 排盘记录ID
     * @param question 用户问题，如"今年适合跳槽吗？"
     * @return AI 建议文本
     */
    Result<String> aiAdvice(Long recordId, String question);
}
