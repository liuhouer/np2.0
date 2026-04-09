package cn.northpark.Xuanaobazi;

/**
 * 日历与节气计算工具类
 * 直接移植原版 XL_class + SSQ_class + MYiDateEx 的核心算法
 */
public class DateCalc {

    private static final double J2000 = 2451545.0;
    private static final double PI    = Math.PI;
    private static final double PI2   = 2 * Math.PI;

    // ===== 儒略日 =====

    public static double toJulian(int y, int m, int d, int h, int min) {
        double day = d + (h + min / 60.0) / 24.0;
        int yy = y, mm = m;
        if (mm <= 2) { mm += 12; yy--; }
        int n = 0;
        if ((y * 372 + m * 31 + d) >= 588829) {
            int c = (int) Math.floor(yy / 100.0);
            n = 2 - c + (int) Math.floor(c / 4.0);
        }
        return Math.floor(365.25 * (yy + 4716))
             + Math.floor(30.6001 * (mm + 1))
             + day + n - 1524.5;
    }

    public static double toJulian(short[] d) {
        return toJulian(d[0], d[1], d[2], d[3], d[4]);
    }

    public static int[] fromJulian(double jd) {
        int D = (int) Math.floor(0.5 + jd);
        double F = (0.5 + jd) - D;
        if (D >= 2299161) {
            int c = (int) Math.floor((D - 1867216.25) / 36524.25);
            D += (c + 1) - (int) Math.floor(c / 4.0);
        }
        int D2 = D + 1524;
        int Y = (int) Math.floor((D2 - 122.1) / 365.25);
        int D3 = D2 - (int) Math.floor(365.25 * Y);
        int M = (int) Math.floor(D3 / 30.601);
        int day = D3 - (int) Math.floor(30.601 * M);
        if (M > 13) { M -= 13; Y -= 4715; }
        else         { M--;    Y -= 4716; }
        double F2 = F * 24.0;
        int hour = (int) Math.floor(F2);
        int minute = (int) Math.floor((F2 - hour) * 60.0);
        return new int[]{Y, M, day, hour, minute};
    }

    public static void julianToShort(double jd, short[] out) {
        int[] r = fromJulian(jd);
        out[0] = (short) r[0]; out[1] = (short) r[1]; out[2] = (short) r[2];
        out[3] = (short) r[3]; out[4] = (short) r[4];
    }

    // ===== 日柱 =====

    public static int getRiZhu(int y, int m, int d) {
        return ((int) Math.floor(toJulian(y, m, d, 12, 0)) + 50) % 60;
    }

    public static int getWeek(int y, int m, int d) {
        return ((int) Math.floor(toJulian(y, m, d, 12, 0)) + 1) % 7;
    }

    // ===== 节气计算（移植自原版 XL_class + SSQ_class.qi_high）=====

    /**
     * 获取立春儒略日（黄经315度）
     * 立春在每年2月初，取该月的前节气
     */
    public static double getLiChunJD(int year) {
        // 立春在2月初，用2月15日作为参考点，取所在月的前节气
        short[] gDate = {(short) year, 2, 15, 12, 0};
        double[] jq = getMonthSanJieQi(gDate, null);
        return jq[0]; // 前节气即立春
    }

    /**
     * 获取月柱三节气（前节气、中气、后节气）的儒略日
     * 对应原版 MYiDateEx.GetYue_SanJieQi
     * @param gDate 公历日期
     * @param iJieQi 输出 [3][5] 三个节气日期（可为null）
     * @return [前节气JD, 中气JD, 后节气JD]
     */
    public static double[] getMonthSanJieQi(short[] gDate, short[][] iJieQi) {
        double dStart = toJulian(gDate);
        int y = gDate[0] - 2000;
        int m = gDate[1] - 1;
        int iVal = m * 30 - 75;

        double T1 = 0, T2 = 0;
        for (int iter = 0; iter < 200; iter++) {
            double W1 = (y + iVal / 360.0 + 1.0) * PI2;
            double W2 = (y + (iVal + 30) / 360.0 + 1.0) * PI2;
            T1 = qi_high(W1);
            T2 = qi_high(W2);
            if (dStart >= T1 && dStart < T2) break;
            if (dStart < T1) iVal -= 30;
            else iVal += 30;
        }

        if (iJieQi != null) {
            julianToShort(T1, iJieQi[0]);
            julianToShort(T2, iJieQi[2]);
        }

        double W3 = (y + (iVal + 15) / 360.0 + 1.0) * PI2;
        double T3 = qi_high(W3);
        if (iJieQi != null) julianToShort(T3, iJieQi[1]);

        return new double[]{T1, T3, T2};
    }

    /**
     * 获取月支（0-11）
     * 对应原版 MYiDateEx.GetYueZhi
     */
    public static short getYueZhi(short[] gDate) {
        double dStart = toJulian(gDate);
        int y = gDate[0] - 2000;
        int m = gDate[1] - 1;
        int iVal = m * 30 - 75;

        for (int iter = 0; iter < 200; iter++) {
            double W1 = (y + iVal / 360.0 + 1.0) * PI2;
            double W2 = (y + (iVal + 30) / 360.0 + 1.0) * PI2;
            double T1 = qi_high(W1);
            double T2 = qi_high(W2);
            if (dStart >= T1 && dStart < T2) break;
            if (dStart < T1) iVal -= 30;
            else iVal += 30;
        }

        int iVal2 = iVal;
        while (iVal2 < 0) iVal2 += 360;
        int i = iVal2 - 285;
        if (i < 0) i += 360;
        return (short) ((i / 30 + 1 + 1) % 12);
    }

    // ===== 干支编码 =====

    public static int encGanZhi(int tian, int di) {
        int t1 = tian - di;
        if (t1 < 0) t1 += 12;
        return ((t1 / 2) * 10) + tian;
    }

    /** 数字转汉字 */
    public static String numToHz(int num) {
        String[] hnum = {"○", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        if (num > 0 && num < 10)  return hnum[num];
        if (num > 10 && num < 20) return "十" + hnum[num % 10];
        if (num > 20 && num < 30) return "廿" + hnum[num % 10];
        if (num == 10) return "十";
        if (num == 20) return "二十";
        if (num == 30) return "三十";
        return String.valueOf(num);
    }

    // ===== 天文计算核心（移植自 XL_class + SSQ_class）=====

    /**
     * 节气精确计算（移植自 SSQ_class.qi_high）
     * W = (y + iVal/360.0 + 1.0) * 2*PI，其中 y=year-2000，iVal=黄经偏移度
     * 返回距J2000的天数（儒略日 - J2000）
     */
    private static double qi_high(double W) {
        double t = S_aLon_t2(W) * 36525.0;
        double t2 = (t - dt_T(t)) + 1.0 / 3.0;
        double v = t2 + 0.5;
        double v2 = (v - Math.floor(v)) * 86400.0;
        double result = (v2 < 1200.0 || v2 > 85200.0)
            ? (S_aLon_t(W) * 36525.0 - dt_T(t2)) + 1.0 / 3.0
            : t2;
        return result + J2000;
    }

    /**
     * 移植自 XL_class.S_aLon_t(W)
     * 给定目标黄经W（弧度），返回儒略世纪数t
     */
    private static double S_aLon_t(double W) {
        double t = ((W - 1.75347) - PI) / 628.3319653318;
        double v = E_v(t);
        double t2 = t + ((W - S_aLon(t, 10)) / v);
        double v2 = E_v(t2);
        return t2 + ((W - S_aLon(t2, -1)) / v2);
    }

    /**
     * 移植自 XL_class.S_aLon_t2(W)（快速版）
     */
    private static double S_aLon_t2(double W) {
        double t = ((W - 1.75347) - PI) / 628.3319653318;
        double t2 = t - (((5.297e-6 * t * t)
            + (0.0334166 * Math.cos(4.669257 + 628.307585 * t))
            + (2.061e-4 * Math.cos(2.67823 + 628.307585 * t) * t))
            / 628.3319653318);
        return t2 + (((W - E_Lon(t2, 8) - PI)
            + (20.5 + 17.2 * Math.sin(2.1824 - 33.75705 * t2)) / 206264.80648278826)
            / 628.3319653318);
    }

    /**
     * 移植自 XL_class.S_aLon(t, n)
     * 太阳视黄经
     */
    private static double S_aLon(double t, int n) {
        return E_Lon(t, n) + nutationLon2(t) + gxc_sunLon(t) + PI;
    }

    /**
     * 移植自 XL_class.E_Lon(t, n) = eph0.XL0_calc(0, 0, t, n)
     * 地球黄经（简化版，使用VSOP87主项）
     */
    private static double E_Lon(double t, int n) {
        // 简化VSOP87 L0项（主要项）
        double L = 1.75347046 + 628.3319653318 * t
            + 5.291838e-6 * t * t;
        // L1项
        L += 3.34166e-2 * Math.cos(4.669257 + 628.307585 * t);
        L += 3.489e-4  * Math.cos(4.6261   + 1256.61517 * t);
        L += 2.061e-4  * Math.cos(2.67823  + 628.307585 * t) * t;
        L += 1.22e-5   * Math.cos(1.8146   + 7771.3771  * t);
        L += 1.164e-5  * Math.cos(2.9244   + 5753.3849  * t);
        L += 9.94e-6   * Math.cos(5.0      + 5507.5532  * t);
        L += 9.42e-6   * Math.cos(0.0      + 5223.6939  * t);
        return rad2rrad(L);
    }

    /**
     * 移植自 XL_class.E_v(t)
     * 地球黄经速度
     */
    private static double E_v(double t) {
        double f = 628.307585 * t;
        return 628.332
            + 21.0  * Math.sin(1.527 + f)
            + 0.44  * Math.sin(1.48  + 2 * f)
            + 0.129 * Math.sin(5.82  + f) * t
            + 5.5e-4 * Math.sin(4.21 + f) * t * t;
    }

    /**
     * 移植自 eph0.nutationLon2(t)
     * 章动经度修正
     */
    private static double nutationLon2(double t) {
        double omega = 2.1824 - 33.75705 * t;
        return (-17.2 * Math.sin(omega)
                - 1.32 * Math.sin(2 * (4.895063168 + 628.331966786 * t))
                - 0.23 * Math.sin(2 * (6.240060 + 628.301955 * t))
                + 0.21 * Math.sin(2 * omega)) / 206264.80648278826;
    }

    /**
     * 移植自 eph0.gxc_sunLon(t)
     * 太阳光行差修正
     */
    private static double gxc_sunLon(double t) {
        double v = -0.043126 + 628.301955 * t - 2.732e-6 * t * t;
        double e = 0.016708634 - 4.2037e-5 * t - 1.267e-7 * t * t;
        return -20.49552 * (1.0 + Math.cos(v) * e) / 206264.80648278826;
    }

    /**
     * 移植自 eph0.dt_T(t)
     * 时间修正（ΔT）
     */
    private static double dt_T(double t) {
        double y = 2000.0 + (t - 0.5) / 365.25;
        return dt_calc(y) / 86400.0;
    }

    private static double dt_calc(double y) {
        double u, dt;
        if (y >= 2005) {
            u = y - 2000;
            dt = 62.92 + u * (0.32217 + u * 0.005589);
        } else if (y >= 1986) {
            u = y - 2000;
            dt = 63.86 + u * (0.3345 + u * (-0.060374 + u * (0.0017275 + u * (0.000651814 + u * 0.00002373599))));
        } else if (y >= 1961) {
            u = y - 1975;
            dt = 45.45 + u * (1.067 + u * (-1.0 / 260.0 + u / -718.0));
        } else if (y >= 1941) {
            u = y - 1950;
            dt = 29.07 + u * (0.407 + u * (-1.0 / 233.0 + u / 2547.0));
        } else if (y >= 1920) {
            u = y - 1920;
            dt = 21.20 + u * (0.84493 + u * (-0.076100 + u * 0.0020936));
        } else if (y >= 1900) {
            u = y - 1900;
            dt = -2.79 + u * (1.494119 + u * (-0.0598939 + u * (0.0061966 + u * -0.000197)));
        } else if (y >= 1860) {
            u = y - 1860;
            dt = 7.62 + u * (0.5737 + u * (-0.251754 + u * (0.01680668 + u * (-0.0004473624 + u / 233174.0))));
        } else if (y >= 1800) {
            u = y - 1800;
            dt = 13.72 + u * (-0.332447 + u * (0.0068612 + u * (0.0041116 + u * (-0.00037436 + u * (0.0000121272 + u * (-0.0000001699 + u * 0.000000000875))))));
        } else if (y >= 1700) {
            u = y - 1700;
            dt = 8.83 + u * (0.1603 + u * (-0.0059285 + u * (0.00013336 + u / -1174000.0)));
        } else if (y >= 1600) {
            u = y - 1600;
            dt = 120 + u * (-0.9808 + u * (-0.01532 + u / 7129.0));
        } else if (y >= 500) {
            u = (y - 1000) / 100.0;
            dt = 1574.2 + u * (-556.01 + u * (71.23472 + u * (0.319781 + u * (-0.8503463 + u * (-0.005050998 + u * 0.0083572073)))));
        } else if (y >= -500) {
            u = y / 100.0;
            dt = 10583.6 + u * (-1014.41 + u * (33.78311 + u * (-5.952053 + u * (-0.1798452 + u * (0.022174192 + u * 0.0090316521)))));
        } else {
            u = (y - 1820) / 100.0;
            dt = -20 + 32 * u * u;
        }
        return dt;
    }

    private static double rad2rrad(double v) {
        return v - PI2 * Math.floor(v / PI2);
    }
}
