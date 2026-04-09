package cn.northpark.Xuanaobazi;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import java.util.Calendar;

/**
 * 八字排盘核心计算引擎
 * 严格按照原版 CBaZiEx.BzRun + Bzping + Bzping2 逻辑实现
 */
public class BaZiCalc {

    // 藏干权重（对应 strArr in Bzping）
    private static final String[] DZC_WEIGHT = {
        "1230", "30", "090318", "300707", "30", "091803",
        "093005", "0930", "031809", "103003", "30", "090318"
    };

    // 十神力量系数（对应 dArr in Bzping）
    private static final double[] SHEN_COEF = {
        2.0, 1.75, 0.9, 1.0, 0.7, 0.8, 0.5, 0.6, 1.5, 1.25
    };

    private BaZiResult r;
    private int year, month, day, hour, minute;
    private boolean isMale;

    // 内部计算用
    private short[] Gda  = new short[5]; // 公历
    private short[] Gda2 = new short[5]; // 原始公历

    public BaZiResult calc(int year, int month, int day, int hour, int minute,
                           boolean isMale, String name) {
        this.year = year; this.month = month; this.day = day;
        this.hour = hour; this.minute = minute; this.isMale = isMale;

        r = new BaZiResult();
        r.name = (name != null) ? name : "";
        r.isMale = isMale;
        r.birthYear = year; r.birthMonth = month; r.birthDay = day;
        r.birthHour = hour; r.birthMinute = minute;

        // 初始化公历数组
        Gda[0] = (short) year;  Gda[1] = (short) month;
        Gda[2] = (short) day;   Gda[3] = (short) hour;
        Gda[4] = (short) minute;
        for (int i = 0; i < 5; i++) Gda2[i] = Gda[i];

        r.dtmpsat = DateCalc.toJulian(year, month, day, hour, minute);

        // 子时换日（23:00-23:59 归入次日子时）
        short[] calcGda = new short[5];
        for (int i = 0; i < 5; i++) calcGda[i] = Gda[i];
        if (hour == 23) {
            int[] next = DateCalc.fromJulian(r.dtmpsat + 1.0 / 24.0);
            calcGda[0] = (short) next[0]; calcGda[1] = (short) next[1];
            calcGda[2] = (short) next[2];
        }

        // 公历转农历（使用 Hutool ChineseDate）
        ChineseDate chineseDate = new ChineseDate(
            DateUtil.parseDate(year + "-" + month + "-" + day));
        r.nlYear   = chineseDate.getChineseYear();
        r.nlMonth  = chineseDate.getMonth();
        r.nlDay    = chineseDate.getDay();
        r.nlRun    = chineseDate.isLeapMonth();
        r.yueTotal = 30; // Hutool 不直接提供月大小，默认30，显示用

        // ===== 年柱 =====
        double lichunJD = DateCalc.getLiChunJD(year);
        int iShengNian = (r.dtmpsat < lichunJD) ? year - 1 : year;
        r.gz[0] = (short) ((iShengNian + 897 + 6000) % 60);

        // ===== 月柱 =====
        short[][] sArr2 = new short[3][5];
        double[] jqJDs = DateCalc.getMonthSanJieQi(Gda2, sArr2);
        r.dtmp1   = jqJDs[0];
        r.zhongQi = jqJDs[1];
        r.dtmpend = jqJDs[2];
        short yueZhi = DateCalc.getYueZhi(Gda2);

        int i6 = r.gz[0] % 10;
        if (i6 == 0) i6 = 10;
        if (i6 > 5) i6 -= 5;
        int i7 = ((i6 * 2) + 1) % 10;
        int i8 = yueZhi - 3;
        if (i8 < 0) i8 += 12;
        r.gz[1] = (short) DateCalc.encGanZhi((i7 + i8) % 10, (i8 + 3) % 12);

        // ===== 日柱 =====
        r.gz[2] = (short) DateCalc.getRiZhu(calcGda[0], calcGda[1], calcGda[2]);

        // ===== 时柱 =====
        int i9 = r.gz[2] % 10;
        if (i9 == 0) i9 = 5;
        int s2h = hour % 2 == 1 ? (hour + 1) % 24 : hour;
        int hourZhi = ((s2h / 2) + 1) % 12;
        int hourGan = (((i9 * 2) - 1) + (s2h / 2)) % 10;
        r.gz[3] = (short) DateCalc.encGanZhi(hourGan, hourZhi);

        // ===== 纳音 =====
        for (int i = 0; i < 4; i++) {
            int g = r.gz[i];
            if (g % 2 == 1) g = (g + 1) % 60;
            r.naYin[i] = BaZiConstants.NAYIN_WX[g / 2];
        }

        // ===== 大运方向 =====
        // 原版逻辑：阳男（年干为阳=奇数索引1,3,5,7,9）顺行，阴男逆行；阴女顺行，阳女逆行
        // 天干索引：癸0甲1乙2丙3丁4戊5己6庚7辛8壬9
        // 阳干：甲丙戊庚壬 = 索引1,3,5,7,9（奇数）
        // 原版：(isMale && gz[0]%2==1) || (!isMale && gz[0]%2==0) → 顺行
        int direction;
        if ((isMale && r.gz[0] % 2 == 1) || (!isMale && r.gz[0] % 2 == 0)) {
            direction = 1;
        } else {
            direction = -1;
        }

        // ===== 起运时间（分钟数）=====
        int iFloor;
        if (direction == 1) {
            iFloor = (int) Math.floor((r.dtmpend - r.dtmpsat) * 1440.0);
        } else {
            iFloor = (int) Math.floor((r.dtmpsat - r.dtmp1) * 1440.0);
        }
        r.yunMin = iFloor;
        r.dtmp1  = jqJDs[0];
        r.dtmpend= jqJDs[2];

        // 起运年龄：每3天=1年，每1天=4个月，每1小时=10天
        int i13 = iFloor / 4320;
        int i14 = (iFloor - i13 * 4320) / 360;
        int i15 = (iFloor - i13 * 4320 - i14 * 360) / 12;

        // 起运日期
        short[] startDate = new short[5];
        for (int i = 0; i < 5; i++) startDate[i] = Gda[i];
        startDate[1] = (short) (startDate[1] + i14);
        if (startDate[1] > 12) { startDate[1] -= 12; startDate[0]++; }
        startDate[0] = (short) (startDate[0] + i13);
        double startJD = DateCalc.toJulian(startDate[0], startDate[1], startDate[2], startDate[3], startDate[4]) + i15;
        int[] sd = DateCalc.fromJulian(startJD);
        r.yunDate[0] = (short) sd[0]; r.yunDate[1] = (short) sd[1]; r.yunDate[2] = (short) sd[2];

        // 起运年龄
        int daYunAge = sd[0] - year + 1;
        if (daYunAge < 1) daYunAge = 1;
        r.daYunAge = daYunAge;
        r.dynian   = (short) daYunAge;
        r.yunYear  = year;

        // ===== 胎元、命宫、身宫 =====
        r.tai = (short) DateCalc.encGanZhi((r.gz[1] % 10 + 1) % 10, (r.gz[1] % 12 + 3) % 12);

        int yueZhi2 = r.gz[1] % 12;
        int shiZhi  = r.gz[3] % 12;
        int i33 = yueZhi2 - 2; if (i33 < 1) i33 += 12;
        int i34 = 2 - i33;     if (i34 < 0) i34 += 12;
        int i35 = 4 - shiZhi;  if (i35 < 0) i35 += 12;
        int mingZhi = (i34 + i35) % 12;
        int yg5 = r.gz[0] % 10; if (yg5 >= 5) yg5 -= 5;
        int i38 = mingZhi - 3; if (i38 < 0) i38 += 12;
        r.ming = (byte) DateCalc.encGanZhi(((yg5 * 2 + 1) + i38) % 10, mingZhi);

        int i40 = yueZhi2 - 2; if (i40 < 1) i40 += 12;
        int i41 = 10 - shiZhi; if (i41 < 0) i41 += 12;
        int shenZhi = ((i40 - i41) + 24) % 12;
        int i44 = shenZhi - 3; if (i44 < 0) i44 += 12;
        r.shen = (byte) DateCalc.encGanZhi(((yg5 * 2 + 1) + i44) % 10, shenZhi);

        // ===== 十神 =====
        calcShiShen();

        // ===== 五行力量（精确计算）=====
        bzping();

        // ===== 运势评分 =====
        calcYunScore(direction);

        return r;
    }

    // ===== 十神计算 =====
    private void calcShiShen() {
        byte monthWxJ = BaZiConstants.DIZHI_WXJ[r.gz[1] % 12];
        byte[] wangMap = {0, 1, 4, 3, 2};

        for (int i = 0; i < 4; i++) {
            if (i != 2) {
                r.tian[i].shiShen  = (byte) qiuLq(r.gz[2], r.gz[i]);
                r.tian[i].shiShenJ = (byte) (r.tian[i].shiShen / 2);
            }
            r.tian[i].diShi = (byte) jiSheng12(r.gz[i] % 10, r.gz[i] % 12);
            int wi = BaZiConstants.TIANGAN_WXJ[r.gz[i] % 10] - monthWxJ;
            if (wi < 0) wi += 5;
            r.tian[i].wang = wangMap[wi];

            r.di[i].shiShen  = (byte) qiuLq(r.gz[2] % 10, BaZiConstants.DIBENGAN[r.gz[i] % 12]);
            r.di[i].shiShenJ = (byte) (r.di[i].shiShen / 2);
            int wi2 = BaZiConstants.DIZHI_WXJ[r.gz[i] % 12] - monthWxJ;
            if (wi2 < 0) wi2 += 5;
            r.di[i].wang = wangMap[wi2];

            String dzc = BaZiConstants.DZC[r.gz[i] % 12];
            for (int j = 0; j < dzc.length(); j++) {
                int cg = dzc.charAt(j) - '0';
                r.di[i].shiShen2[j]  = (byte) qiuLq(r.gz[2] % 10, cg);
                r.di[i].shiShen2J[j] = (byte) (r.di[i].shiShen2[j] / 2);
            }
        }
    }

    // ===== 五行力量精确计算（对应原版 Bzping + Bzping2）=====
    private void bzping() {
        // 月支本气天干（用于计算旺衰系数）
        short s2ref = BaZiConstants.DIBENGAN[r.gz[1] % 12];

        // tagScore：月支对日主的生助/克泄基础分
        float f = (float) (36.0 * SHEN_COEF[qiuLq(r.gz[2], s2ref)]);
        for (int i = 0; i < 4; i++) {
            int g12 = jiSheng12(r.gz[2] % 10, r.gz[i] % 12);
            if (g12 == 0 || g12 == 2 || g12 == 3 || g12 == 4) f += 18.0f;
            else if (g12 == 5 || g12 == 6 || g12 == 7 || g12 == 9) f -= 18.0f;
        }
        r.tagScore = (short) f;

        // 计算各天干五行力量
        for (int i = 0; i < 10; i++) r.wuXing[i] = 0;

        for (int i = 0; i < 4; i++) {
            // 天干
            byte tgWx = BaZiConstants.TIANGAN_WX[r.gz[i] % 10];
            short s3 = (short) (36.0 * SHEN_COEF[qiuLq(r.gz[i], s2ref)]);
            r.wuXing[tgWx] += s3;

            // 地支藏干
            String dzc = BaZiConstants.DZC[r.gz[i] % 12];
            String wt  = DZC_WEIGHT[r.gz[i] % 12];
            for (int j = 0; j < dzc.length(); j++) {
                int cg = dzc.charAt(j) - '0';
                byte cgWx = BaZiConstants.TIANGAN_WX[cg];
                int wv = Integer.parseInt(wt.substring(j * 2, j * 2 + 2));
                short s5 = (short) (wv * SHEN_COEF[qiuLq(cg, s2ref)]);
                r.wuXing[cgWx] += s5;
            }
        }

        // 阴阳分数
        r.yyScore[0] = 0; r.yyScore[1] = 0;
        for (int i = 0; i < 10; i++) {
            r.yyScore[i % 2] += r.wuXing[i];
        }

        // 十神力量
        for (int i = 0; i < 10; i++) {
            int tg = sshengTgan(r.gz[2], (short) i);
            r.shiShen[i] = r.wuXing[BaZiConstants.TIANGAN_WX[tg]];
        }

        // 合计
        for (int i = 0; i < 5; i++) {
            r.wuXingJ[i]  = (short) (r.wuXing[i * 2] + r.wuXing[i * 2 + 1]);
            r.shiShenJ[i] = (short) (r.shiShen[i * 2] + r.shiShen[i * 2 + 1]);
        }

        // 综合分 = tagScore + (比劫+印枭) - (食伤+财+官杀)
        int t14 = r.shiShenJ[0] + r.shiShenJ[4];
        int t15 = r.shiShenJ[1] + r.shiShenJ[2] + r.shiShenJ[3];
        int ntol2 = r.tagScore + (t14 - t15);
        r.score = (short) ntol2;

        // 日元旺衰判断（严格按原版阈值，不启用从格）
        if (ntol2 >= 0 && ntol2 <= 71)        { r.riYuan = 3; r.riYong = 3; }
        else if (ntol2 >= 72 && ntol2 <= 143) { r.riYuan = 4; r.riYong = 4; }
        else if (ntol2 >= -72 && ntol2 <= -1) { r.riYuan = 2; r.riYong = 2; }
        else if (ntol2 < -72)                 { r.riYuan = 1; r.riYong = 1; }
        else                                  { r.riYuan = 5; r.riYong = 5; }

        // 用神推算
        int riWx = BaZiConstants.TIANGAN_WX[r.gz[2] % 10] / 2;
        int yongWx;
        switch (r.riYong) {
            case 0: case 2: yongWx = (riWx + 4) % 5; break;
            case 1:         yongWx = riWx; break;
            case 3:         yongWx = (riWx + 4) % 5; break;
            case 4: case 6: yongWx = (riWx + 2) % 5; break;
            default:        yongWx = (riWx + 3) % 5; break;
        }
        r.wsYong[0][0] = (byte) yongWx;
        int t26 = yongWx - riWx;
        if (t26 < 0) t26 += 5;
        r.wsYong[1][0] = (byte) t26;

        // 四神（喜忌仇闲）
        byte[][] byong = {
            {4, 3, 2, 1}, {2, 4, 0, 3}, {1, 0, 4, 3}, {2, 0, 4, 1}, {0, 2, 1, 3}
        };
        int riWxJ = BaZiConstants.TIANGAN_WXJ[r.gz[2] % 10];
        for (int i = 0; i < 4; i++) {
            int t12 = (byong[r.wsYong[1][0]][i] + riWxJ) % 5;
            r.wsYong[0][i + 1] = (byte) t12;
            int t13 = t12 - riWxJ;
            if (t13 < 0) t13 += 5;
            r.wsYong[1][i + 1] = (byte) t13;
        }
    }

    // ===== 运势评分（对应原版 CalyqScore）=====
    private void calcYunScore(int direction) {
        double[] fnum = {0.95, 0.5, -0.95, -0.5, 0.0};

        // 大运分（每步前后五年）
        for (int step = 0; step < 9; step++) {
            int gz = ((r.gz[1] + (step + 1) * direction) % 60 + 60) % 60;
            // 前五年（step*2）
            double ft1 = fnum[gan5Shen(gz % 10)] * 0.7 + fnum[gan5Shen(BaZiConstants.DIBENGAN[gz % 12])] * 0.3;
            r.dyun[step * 2] = (byte) ((int)(ft1 * 50) + 50);
            // 后五年（step*2+1）
            double ft2 = fnum[gan5Shen(gz % 10)] * 0.3 + fnum[gan5Shen(BaZiConstants.DIBENGAN[gz % 12])] * 0.7;
            r.dyun[step * 2 + 1] = (byte) ((int)(ft2 * 50) + 50);
        }

        // 流年分 + 小运分 + 综合分（1-100岁）
        for (int age = 1; age <= 100; age++) {
            int lnianGz = ((r.yunYear + age - 1 + 897 + 6000) % 60);
            int xxianGz  = ((r.gz[3] + direction * age) % 60 + 60) % 60;

            double ft3 = (fnum[gan5Shen(lnianGz % 10)] * 0.35 + fnum[gan5Shen(BaZiConstants.DIBENGAN[lnianGz % 12])] * 0.65) * 50;
            r.lnian[age - 1] = (byte) ((int) ft3 + 50);

            double ft4 = (fnum[gan5Shen(xxianGz % 10)] * 0.35 + fnum[gan5Shen(BaZiConstants.DIBENGAN[xxianGz % 12])] * 0.65) * 50;
            r.xxian[age - 1] = (byte) ((int) ft4 + 50);

            if (age < r.daYunAge) {
                r.zscore[age - 1] = (byte) ((r.lnian[age - 1] + r.xxian[age - 1]) / 2);
            } else {
                int nystep = (age - r.dynian) / 5;
                if (nystep < 0) nystep = 0;
                if (nystep >= 18) nystep = 17;
                r.zscore[age - 1] = (byte) ((r.lnian[age - 1] + r.dyun[nystep]) / 2);
            }
        }
    }

    // ===== 工具方法 =====

    /** 求十神（日主天干 vs 目标天干） */
    public int qiuLq(int riGz, int qGz) {
        int t1 = riGz % 10;
        int t2 = qGz % 10;
        if (t2 < t1) t2 += 10;
        if (t1 % 2 == 0 && (t2 - t1) % 2 == 1) t2 += 2;
        return (t2 - t1) % 10;
    }

    /** 十二长生 */
    public int jiSheng12(int tian, int di) {
        int t1 = BaZiConstants.G12DUI[tian];
        int t2 = (tian % 2 == 1) ? di - t1 : t1 - di;
        return (t2 % 12 + 12) % 12;
    }

    /** 天干对应五神（用神类别） */
    private int gan5Shen(int gan) {
        int wx = BaZiConstants.TIANGAN_WX[gan] / 2;
        for (int i = 0; i < 5; i++) {
            if (r.wsYong[0][i] == wx) return i;
        }
        return 0;
    }

    /** 推算天干（Ssheng_Tgan） */
    private int sshengTgan(short agan, short ssheng) {
        short t1 = (short) (((agan % 10) + ssheng) % 10);
        if (agan % 2 == 0 && ssheng % 2 == 1) {
            short t12 = (short) (t1 - 2);
            return t12 < 0 ? t12 + 10 : t12;
        }
        return t1;
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
