package cn.northpark.Xuanaobazi.vo;

import lombok.Data;
import java.util.List;

/**
 * 运势分析报告 VO（1-70岁）
 * 对应 YunQiCalc.generateFullReport() 的所有输出模块
 */
@Data
public class BaZiYunVO {

    /** 未交大运前的流年列表（age < daYunAge） */
    private List<LiuNianVO> preYun;

    /** 各步大运（最多9步，到70岁截止） */
    private List<DaYunVO> daYunList;

    // =========================================================
    // 内部类：单步大运
    // =========================================================
    @Data
    public static class DaYunVO {
        /** 第几步（1-9） */
        private int step;
        /** 步骤汉字序号（如"一"） */
        private String stepName;
        /** 干支（如"甲子"） */
        private String ganZhi;
        /** 天干 */
        private String tianGan;
        /** 地支 */
        private String diZhi;
        /** 十神 */
        private String shiShen;
        /** 十二长生 */
        private String g12;
        /** 管辖起始年 */
        private int startYear;
        /** 管辖结束年 */
        private int endYear;
        /** 前五年运势分 */
        private int scoreFront;
        /** 后五年运势分 */
        private int scoreBack;
        /** 天干用神属性（如"喜用"） */
        private String tianGanYongShen;
        /** 地支用神属性 */
        private String diZhiYongShen;
        /** 大运分析文字 */
        private String analysis;
        /** 是否逢空亡 */
        private boolean kongWang;
        /** 该步大运下的流年列表 */
        private List<LiuNianVO> liuNianList;
    }

    // =========================================================
    // 内部类：单岁流年
    // =========================================================
    @Data
    public static class LiuNianVO {
        /** 年龄 */
        private int age;
        /** 公历年份 */
        private int calYear;
        /** 流年干支 */
        private String liuNianGanZhi;
        /** 流年天干 */
        private String liuNianTianGan;
        /** 流年地支 */
        private String liuNianDiZhi;
        /** 小运干支 */
        private String xiaoYunGanZhi;
        /** 流年十神 */
        private String liuNianShiShen;
        /** 流年十二长生 */
        private String liuNianG12;
        /** 天干用神属性 */
        private String tianGanYongShen;
        /** 地支用神属性 */
        private String diZhiYongShen;
        /** 小限运势分（未交大运前有效） */
        private int xiaoFen;
        /** 流年运势分 */
        private int liuNianFen;
        /** 综合运势分 */
        private int zongHeFen;
        /** 运势等级：优/良/中/差（根据综合分换算） */
        private String scoreLevel;
        /** 特殊关系描述（岁运并临、天克地冲等） */
        private List<String> specialRelations;
        /** 天干合信息（如"甲己合，与月干之合"） */
        private List<String> ganHe;
        /** 地支冲信息 */
        private List<String> zhiChong;
        /** 流年详细分析文字 */
        private String analysis;
        /** 流年神煞描述 */
        private String shenSha;
        /** 64卦（16岁以上且已交大运） */
        private String gua64;
    }
}
