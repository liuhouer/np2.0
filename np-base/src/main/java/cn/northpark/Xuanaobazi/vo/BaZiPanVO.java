package cn.northpark.Xuanaobazi.vo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import java.util.List;

/**
 * 八字排盘完整结果 VO
 * 对应 BaZiEngine.formatPanResult() 的所有输出模块
 */
@Data
public class BaZiPanVO {

    /** 基本信息 */
    private BasicInfo basicInfo;

    /** 四柱（年/月/日/时） */
    private List<PillarVO> pillars;

    /** 节气信息 */
    private JieQiVO jieQi;

    /** 大运（最多8步） */
    private DaYunSummary daYun;

    /** 胎元、命宫、身宫 */
    private SpecialStars specialStars;

    /** 神煞（四柱各自） */
    private ShenShaVO shenSha;

    /** 星座、生肖、文昌、天乙 */
    private XingZuoEtcVO xingZuoEtc;

    /** 五行力量 */
    private WuXingVO wuXing;

    /** 日元旺衰 + 用神 */
    private RiYuanYongShenVO riYuanYongShen;

    // =========================================================
    // 内部类：基本信息
    // =========================================================
    @Data
    public static class BasicInfo {
        /** 姓名（可为空） */
        private String name;
        /** 性别：男/女 */
        private String gender;
        /** 阳历年 */
        private int solarYear;
        /** 阳历月 */
        private int solarMonth;
        /** 阳历日 */
        private int solarDay;
        /** 阳历时 */
        private int solarHour;
        /** 阳历分 */
        private int solarMinute;
        /** 星期（一~日） */
        private String weekDay;
        /** 农历年（汉字，如"辛未"） */
        private String lunarYear;
        /** 农历月（如"正月"、"闰三月"） */
        private String lunarMonth;
        /** 农历日（如"初一"） */
        private String lunarDay;
        /** 时辰地支（如"未"） */
        private String lunarHourZhi;
        /** 月大小 */
        private String lunarMonthSize;
    }

    // =========================================================
    // 内部类：单柱信息
    // =========================================================
    @Data
    public static class PillarVO {
        /** 柱名：年柱/月柱/日柱/时柱 */
        private String pillarName;
        /** 天干（如"甲"） */
        private String tianGan;
        /** 地支（如"子"） */
        private String diZhi;
        /** 干支合称（如"甲子"） */
        private String ganZhi;
        /** 纳音五行（如"海中金"） */
        private String naYin;
        /** 天干十神（如"比肩"） */
        private String tianGanShiShen;
        /** 地支本气十神 */
        private String diZhiShiShen;
        /** 地支藏干十神（最多3个） */
        private List<String> diZhiCangGanShiShen;
        /** 十二长生（如"长生"） */
        private String g12;
        /** 是否有三合 */
        private boolean sanHe;
        /** 是否有三会 */
        private boolean sanHui;
        /** 是否有冲 */
        private boolean chong;
        /** 是否有合 */
        private boolean he;
        /** 是否有刑 */
        private boolean xing;
    }

    // =========================================================
    // 内部类：节气
    // =========================================================
    @Data
    public static class JieQiVO {
        /** 前节气名称（如"大雪"） */
        private String prevJieQiName;
        /** 前节气日期（yyyy-MM-dd HH:mm） */
        private String prevJieQiDate;
        /** 后节气名称（如"冬至"） */
        private String nextJieQiName;
        /** 后节气日期 */
        private String nextJieQiDate;
    }

    // =========================================================
    // 内部类：大运汇总
    // =========================================================
    @Data
    public static class DaYunSummary {
        /** 顺/逆行 */
        private String direction;
        /** 起运年龄 */
        private int startAge;
        /** 起运年份 */
        private int startYear;
        /** 8步大运列表 */
        private List<DaYunStepVO> steps;
        /** 流年表（10行×8列，每格为干支字符串） */
        private List<List<String>> liuNianTable;
        /** 流年表起始年份 */
        private int liuNianStartYear;
        /** 流年表结束年份 */
        private int liuNianEndYear;
    }

    @Data
    public static class DaYunStepVO {
        /** 第几步（1-8） */
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
        /** 起始年龄 */
        private int startAge;
        /** 起始年份 */
        private int startYear;
    }

    // =========================================================
    // 内部类：胎元/命宫/身宫
    // =========================================================
    @Data
    public static class SpecialStars {
        /** 胎元干支 */
        private String taiYuan;
        /** 胎元纳音 */
        private String taiYuanNaYin;
        /** 命宫干支 */
        private String mingGong;
        /** 命宫纳音 */
        private String mingGongNaYin;
        /** 身宫干支 */
        private String shenGong;
        /** 身宫纳音 */
        private String shenGongNaYin;
    }

    // =========================================================
    // 内部类：神煞
    // =========================================================
    @Data
    @JsonPropertyOrder({"nianZhu", "yueZhu", "riZhu", "shiZhu"})
    public static class ShenShaVO {
        /** 年柱神煞列表（如["天乙","驿马"]） */
        private List<String> nianZhu;
        /** 月柱神煞列表 */
        private List<String> yueZhu;
        /** 日柱神煞列表 */
        private List<String> riZhu;
        /** 时柱神煞列表 */
        private List<String> shiZhu;
    }

    // =========================================================
    // 内部类：星座/生肖/文昌/天乙
    // =========================================================
    @Data
    public static class XingZuoEtcVO {
        /** 星座（如"摩羯座"） */
        private String xingZuo;
        /** 生肖（如"羊"） */
        private String shengXiao;
        /** 文昌位方向（如"卯方、即东方"） */
        private String wenChangDir;
        /** 天乙贵人地支1 */
        private String tianYi1;
        /** 天乙贵人地支2 */
        private String tianYi2;
    }

    // =========================================================
    // 内部类：五行力量
    // =========================================================
    @Data
    public static class WuXingVO {
        /** 水的力量值 */
        private int shui;
        /** 水的占比（%） */
        private double shuiPct;
        /** 木的力量值 */
        private int mu;
        private double muPct;
        /** 火的力量值 */
        private int huo;
        private double huoPct;
        /** 土的力量值 */
        private int tu;
        private double tuPct;
        /** 金的力量值 */
        private int jin;
        private double jinPct;
        /** 己生助（比劫+印）力量 */
        private int shengZhu;
        private double shengZhuPct;
        /** 克泄耗（食伤+财+官）力量 */
        private int keXie;
        private double keXiePct;
        /** 阴气分值 */
        private int yinScore;
        private double yinPct;
        /** 阳气分值 */
        private int yangScore;
        private double yangPct;
    }

    // =========================================================
    // 内部类：日元旺衰 + 用神
    // =========================================================
    @Data
    public static class RiYuanYongShenVO {
        /** 日主综合得分 */
        private int score;
        /** 旺衰描述（如"偏弱"） */
        private String riYuanDesc;
        /** 喜用神五行（如"水"） */
        private String xiYong1;
        /** 喜用神五行2（可为空） */
        private String xiYong2;
        /** 忌仇神五行 */
        private String jiChou1;
        /** 忌仇神五行2（可为空） */
        private String jiChou2;
    }
}
