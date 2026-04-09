package cn.northpark.Xuanaobazi;

/**
 * 八字排盘结果数据类
 */
public class BaZiResult {

    // ===== 输入 =====
    public String name = "";
    public boolean isMale = true;
    public int birthYear, birthMonth, birthDay, birthHour, birthMinute;

    // ===== 四柱干支 [0]=年 [1]=月 [2]=日 [3]=时 =====
    public short[] gz = new short[4];

    // ===== 农历 =====
    public int nlYear, nlMonth, nlDay;
    public boolean nlRun;
    public int yueTotal; // 月大小（天数）

    // ===== 纳音五行 =====
    public byte[] naYin = new byte[4];

    // ===== 十神（天干/地支） =====
    public PillarInfo[] tian = new PillarInfo[4];
    public PillarInfo[] di   = new PillarInfo[4];

    // ===== 五行力量（精确权重计算，阴阳分开，共10项） =====
    public short[] wuXing  = new short[10]; // 0=阴水,1=阳水,2=阴木,3=阳木,...
    public short[] shiShen = new short[10]; // 十神力量

    // ===== 五行/六亲合计 =====
    public short[] wuXingJ  = new short[5]; // 水木火土金
    public short[] shiShenJ = new short[5]; // 比劫食伤财官印

    // ===== 阴阳分数 =====
    public short[] yyScore = new short[2]; // [0]=阴, [1]=阳

    // ===== 日元旺衰 =====
    public short tagScore; // 基础分
    public short score;    // 综合分
    public byte riYuan;    // 0从旺1太弱2偏弱3平和4偏旺5太旺6从弱
    public byte riYong;    // 同riYuan（用于用神推算）

    // ===== 用神 wsYong[0]=五行属性, wsYong[1]=十神类别 =====
    public byte[][] wsYong = new byte[2][5];

    // ===== 大运 =====
    public int daYunAge;        // 起运年龄
    public int yunYear;         // 起运基准年（出生年）
    public short[] yunDate = new short[3]; // 起运公历日期
    public int yunMin;          // 起运分钟数
    public short dynian;        // 起运年龄（short）

    // ===== 特殊 =====
    public short tai;   // 胎元
    public byte ming;   // 命宫
    public byte shen;   // 身宫

    // ===== 运势评分 =====
    public byte[] dyun  = new byte[20]; // 大运分（每步前后五年）
    public byte[] lnian = new byte[100]; // 流年分
    public byte[] xxian = new byte[100]; // 小运分
    public byte[] zscore= new byte[100]; // 综合分

    // ===== 节气信息 =====
    public double dtmpsat;  // 出生儒略日
    public double dtmp1;    // 前节气JD
    public double dtmpend;  // 后节气JD
    public double zhongQi;  // 中气JD

    // ===== 神煞 =====
    public byte[][] shenSha = new byte[4][40]; // [柱][神煞类型]

    // ===== 格局 =====
    public short bianGe = -1;

    // ===== 星座 =====
    public byte xingZuo;

    public BaZiResult() {
        for (int i = 0; i < 4; i++) {
            tian[i] = new PillarInfo();
            di[i]   = new PillarInfo();
        }
    }

    public static class PillarInfo {
        public byte shiShen  = -1;
        public byte shiShenJ = -1;
        public byte diShi    = -1;
        public byte wang     = -1;
        public byte yong     = -1; // 用神类别
        public byte[] shiShen2  = {-1, -1, -1};
        public byte[] shiShen2J = {-1, -1, -1};
        public int sanHe  = -1;
        public int sanHui = -1;
        public int chong  = -1;
        public boolean bHe   = false;
        public boolean bXing = false;
    }

    public String getTianGan(int p) { return BaZiConstants.TIANGAN[gz[p] % 10]; }
    public String getDiZhi(int p)   { return BaZiConstants.DIZHI[gz[p] % 12]; }
    public String getPillarName(int p) { return getTianGan(p) + getDiZhi(p); }

    public String getNaYinName(int p) {
        int g = gz[p];
        if (g % 2 == 1) g = (g + 1) % 60;
        return BaZiConstants.NAYIN[g / 2];
    }
}
