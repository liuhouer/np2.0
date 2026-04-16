package cn.northpark.Xuanaobazi;

import cn.northpark.Xuanaobazi.vo.BaZiPanVO;
import cn.northpark.Xuanaobazi.vo.BaZiYunVO;

/**
 * 八字排盘主引擎 - 统一对外接口
 *
 * 使用示例：
 *   BaZiEngine engine = new BaZiEngine();
 *   String pan = engine.getPanResult(1991, 12, 31, 13, 10, true, "张三");
 *   String yun = engine.getYunReport(1991, 12, 31, 13, 10, true, "张三");
 *   BaZiPanVO panVO = engine.getPanVO(1991, 12, 31, 13, 10, true, "张三");
 *   BaZiYunVO yunVO = engine.getYunVO(1991, 12, 31, 13, 10, true, "张三");
 */
public class BaZiEngine {

    private final BaZiCalc calc = new BaZiCalc();

    /**
     * 输出1：八字排盘结果
     */
    public String getPanResult(int year, int month, int day, int hour, int minute,
                                boolean isMale, String name) {
        BaZiResult r = calc.calc(year, month, day, hour, minute, isMale, name);
        return formatPanResult(r);
    }

    /**
     * 输出2：运势分析报告（1-70岁）
     */
    public String getYunReport(int year, int month, int day, int hour, int minute,
                                boolean isMale, String name) {
        BaZiResult r = calc.calc(year, month, day, hour, minute, isMale, name);
        return new YunQiCalc(r).generateFullReport();
    }

    /**
     * 同时获取排盘和运势
     * @return [0]=排盘结果, [1]=运势报告
     */
    public String[] getFullResult(int year, int month, int day, int hour, int minute,
                                   boolean isMale, String name) {
        BaZiResult r = calc.calc(year, month, day, hour, minute, isMale, name);
        return new String[]{formatPanResult(r), new YunQiCalc(r).generateFullReport()};
    }

    /**
     * 获取原始结果对象（供MBTI结合等进一步处理）
     */
    public BaZiResult getRawResult(int year, int month, int day, int hour, int minute,
                                    boolean isMale, String name) {
        return calc.calc(year, month, day, hour, minute, isMale, name);
    }

    /**
     * 获取排盘 VO（结构化数据）
     */
    public BaZiPanVO getPanVO(int year, int month, int day, int hour, int minute,
                              boolean isMale, String name) {
        BaZiResult r = calc.calc(year, month, day, hour, minute, isMale, name);
        return buildPanVO(r);
    }

    /**
     * 获取运势 VO（结构化数据）
     */
    public BaZiYunVO getYunVO(int year, int month, int day, int hour, int minute,
                              boolean isMale, String name) {
        BaZiResult r = calc.calc(year, month, day, hour, minute, isMale, name);
        return buildYunVO(r);
    }

    // ===== 排盘格式化（对应原版 BzRun 输出格式）=====

    private String formatPanResult(BaZiResult r) {
        StringBuilder sb = new StringBuilder();

        // 基本信息
        sb.append(String.format("%s  \n", r.name.isEmpty() ? "（未填）" : r.name));

        sb.append(String.format("%s  \n", r.isMale ? "男" : "女"));

        int dispYear = r.birthYear <= 0 ? r.birthYear - 1 : r.birthYear;
        sb.append(String.format("阳历：%d-%d-%d %02d:%02d(北京时间),星期%s\n",
            dispYear, r.birthMonth, r.birthDay, r.birthHour, r.birthMinute,
            BaZiConstants.WEEK[DateCalc.getWeek(r.birthYear, r.birthMonth, r.birthDay)]));

        // 农历
        String nlYearStr = DateCalc.numToHz(r.nlYear > 0 ? r.nlYear : -r.nlYear);
        String nlMonthStr = r.nlMonth == 1 ? "正月" : DateCalc.numToHz(r.nlMonth) + "月";
        if (r.nlRun) nlMonthStr = "闰" + nlMonthStr;
        String nlDayStr = r.nlDay <= 10 ? "初" + DateCalc.numToHz(r.nlDay) : DateCalc.numToHz(r.nlDay) + "日";
        // 时辰
        int shiZhi = ((r.birthHour % 2 == 1 ? (r.birthHour + 1) % 24 : r.birthHour) / 2 + 1) % 12;
        sb.append(String.format("阴历：%s年%s%s%s时(%s)\n\n",
            nlYearStr, nlMonthStr, nlDayStr,
            BaZiConstants.DIZHI[shiZhi],
            r.yueTotal >= 30 ? "大月" : "小月"));

        // 四柱
        String qianZao = r.isMale ? "乾造：　" : "坤造：　";
        sb.append(qianZao);
        for (int i = 0; i < 4; i++) {
            sb.append(r.getPillarName(i)).append("　");
        }
        sb.append("\n\n");

        // 节气
        int yueZhiIdx = (r.gz[1] % 12) - 1;
        if (yueZhiIdx < 1) yueZhiIdx += 12;
        String jq1Name = BaZiConstants.JIEQI[(yueZhiIdx - 1) * 2];
        String jq2Name = BaZiConstants.JIEQI[((yueZhiIdx) % 12) * 2];
        int[] jq1Date = DateCalc.fromJulian(r.dtmp1);
        int[] jq2Date = DateCalc.fromJulian(r.dtmpend);
        int jq1Year = jq1Date[0] <= 0 ? jq1Date[0] - 1 : jq1Date[0];
        int jq2Year = jq2Date[0] <= 0 ? jq2Date[0] - 1 : jq2Date[0];
        sb.append(String.format("%s:%d-%02d-%02d %02d:%02d  %s:%d-%02d-%02d %02d:%02d\n",
            jq1Name, jq1Year, jq1Date[1], jq1Date[2], jq1Date[3], jq1Date[4],
            jq2Name, jq2Year, jq2Date[1], jq2Date[2], jq2Date[3], jq2Date[4]));

        // 大运
        int direction = (r.isMale && r.gz[0] % 2 == 1) || (!r.isMale && r.gz[0] % 2 == 0) ? 1 : -1;
        sb.append("排大运：\n ");
        String str19 = " ", str20 = " ", str21 = " ";
        for (int i = 1; i <= 8; i++) {
            int gz = ((r.gz[1] + i * direction) % 60 + 60) % 60;
            int ss = calc.qiuLq(r.gz[2], gz);
            sb.append(String.format("%s　  ", BaZiConstants.SHISHEN_SHORT[ss]));
            str19 += String.format("%s%s  ", BaZiConstants.TIANGAN[gz % 10], BaZiConstants.DIZHI[gz % 12]);
            String g12s = BaZiConstants.G12[calc.jiSheng12(r.gz[2] % 10, gz % 12)];
            if (g12s.length() == 1) g12s += "　";
            str20 += g12s + "  ";
            String ageStr = String.valueOf(r.daYunAge + (i - 1) * 10);
            while (ageStr.length() < 7) ageStr += " ";
            str21 += ageStr;
        }
        sb.append("\n").append(str19).append("\n");
        sb.append(str20).append("\n");
        sb.append(str21).append("\n");

        // 大运年份行
        String str24 = "";
        int startYear = r.dynian + r.yunYear - 1;
        for (int i = 0; i < 8; i++) {
            int yr = startYear + i * 10;
            if (yr <= 0) yr--;
            String yrStr = String.valueOf(yr);
            while (yrStr.length() < 5) yrStr += " ";
            str24 += yrStr;
        }
        sb.append(str24).append("\n\n");

        // 流年表（10行×8列）
        for (int row = 0; row < 10; row++) {
            String rowStr = "";
            for (int col = 0; col < 8; col++) {
                int yr = startYear + row + col * 10;
                int gz = ((yr + 897 + 6000) % 60);
                rowStr += String.format("%s%s  ",
                    BaZiConstants.TIANGAN[gz % 10], BaZiConstants.DIZHI[gz % 12]);
            }
            sb.append(rowStr).append("\n");
        }

        // 大运年份第二行
        String str25 = "";
        for (int i = 0; i < 8; i++) {
            int yr = startYear + 9 + i * 10;
            if (yr <= 0) yr--;
            String yrStr = String.valueOf(yr);
            while (yrStr.length() < 5) yrStr += " ";
            str25 += yrStr;
        }
        sb.append(str25).append("\n\n");

        // 胎元、命宫、身宫
        sb.append(String.format("※胎元：%s%s(%s)　命宫：%s%s(%s)　身宫：%s%s(%s)\n\n",
            BaZiConstants.TIANGAN[r.tai % 10], BaZiConstants.DIZHI[r.tai % 12],
            getNaYinName(r.tai),
            BaZiConstants.TIANGAN[r.ming % 10], BaZiConstants.DIZHI[r.ming % 12],
            getNaYinName(r.ming),
            BaZiConstants.TIANGAN[r.shen % 10], BaZiConstants.DIZHI[r.shen % 12],
            getNaYinName(r.shen)));

        // 神煞
        sb.append(formatShenSha(r));

        // 星座、生肖、文昌、天乙
        sb.append(formatXingZuoEtc(r));

        // 五行力量
        sb.append("---------------------------------------------------------\n");
        sb.append("命局生克制化：\n");
        int total = 0;
        for (short v : r.wuXingJ) total += v;
        sb.append("力量：");
        for (int i = 0; i < 5; i++) {
            double pct = total > 0 ? r.wuXingJ[i] * 100.0 / total : 0;
            sb.append(String.format("%s%d(%.1f%%)  ", BaZiConstants.WUXING[i], r.wuXingJ[i], pct));
        }
        sb.append("\n");

        int shengZhu = r.shiShenJ[0] + r.shiShenJ[4];
        int keXie    = r.shiShenJ[1] + r.shiShenJ[2] + r.shiShenJ[3];
        int szTotal  = shengZhu + keXie;
        sb.append(String.format("己生助:%d(%.1f%%)  克泄耗:%d(%.1f%%)。  阴气:%d(%.1f%%)  阳气:%d(%.1f%%)。\n\n",
            shengZhu, szTotal > 0 ? shengZhu * 100.0 / szTotal : 0,
            keXie,    szTotal > 0 ? keXie    * 100.0 / szTotal : 0,
            r.yyScore[0], (r.yyScore[0] + r.yyScore[1]) > 0 ? r.yyScore[0] * 100.0 / (r.yyScore[0] + r.yyScore[1]) : 0,
            r.yyScore[1], (r.yyScore[0] + r.yyScore[1]) > 0 ? r.yyScore[1] * 100.0 / (r.yyScore[0] + r.yyScore[1]) : 0));

        // 日元旺衰
        sb.append(String.format("※日主综合得分：%d。 日元%s，",
            r.score, getRiYuanDesc(r.riYuan)));

        // 用神
        sb.append(String.format("喜用%s，%s。忌仇%s，%s。\n",
            BaZiConstants.WUXING[r.wsYong[0][0]],
            r.wsYong[0].length > 1 ? BaZiConstants.WUXING[r.wsYong[0][1]] : "",
            r.wsYong[0].length > 2 ? BaZiConstants.WUXING[r.wsYong[0][2]] : "",
            r.wsYong[0].length > 3 ? BaZiConstants.WUXING[r.wsYong[0][3]] : ""));

        return sb.toString();
    }

    private String getRiYuanDesc(int riYuan) {
        switch (riYuan) {
            case 0: return "从旺";
            case 1: return "太弱";
            case 2: return "偏弱";
            case 3: return "平和";
            case 4: return "偏旺";
            case 5: return "太旺";
            case 6: return "从弱";
            default: return "平和";
        }
    }

    private String getNaYinName(int gz) {
        if (gz % 2 == 1) gz = (gz + 1) % 60;
        return BaZiConstants.NAYIN[gz / 2];
    }

    // ===== 神煞计算（对应原版 shengsha + CalTwo）=====

    private String formatShenSha(BaZiResult r) {
        StringBuilder sb = new StringBuilder();
        sb.append("吉神凶煞：\n");
        String[] pillarNames = {"年柱", "月柱", "日柱", "时柱"};
        for (int p = 0; p < 4; p++) {
            String sha = calcShenSha(r, p);
            if (!sha.isEmpty()) {
                sb.append("  ").append(pillarNames[p]).append(" ").append(sha).append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * 计算指定柱的神煞（移植自原版 shengsha 方法）
     */
    private String calcShenSha(BaZiResult r, int pillar) {
        StringBuilder sb = new StringBuilder();
        int tgp = r.gz[pillar] % 10;  // 天干索引
        int dzp = r.gz[pillar] % 12;  // 地支索引
        int tri = r.gz[2] % 10;       // 日主天干
        int riZhi = r.gz[2] % 12;     // 日主地支

        // 天乙贵人（以日主天干为准）
        boolean tianYi = false;
        switch (tri) {
            case 0: case 9: tianYi = (dzp == 4 || dzp == 6); break;
            case 1: case 5: tianYi = (dzp == 2 || dzp == 8); break;
            case 2: case 6: tianYi = (dzp == 1 || dzp == 9); break;
            case 3: case 4: tianYi = (dzp == 0 || dzp == 10); break;
            default:        tianYi = (dzp == 3 || dzp == 7); break;
        }
        if (tianYi) sb.append("天乙 ");

        // 太极贵人（以日主天干为准）
        boolean taiJi = false;
        switch (tri) {
            case 1: case 2: taiJi = (dzp == 1 || dzp == 7); break;
            case 3: case 4: taiJi = (dzp == 10 || dzp == 4); break;
            case 5: case 6: taiJi = (dzp == 2 || dzp == 5 || dzp == 8 || dzp == 11); break;
            case 7: case 8: taiJi = (dzp == 3 || dzp == 0); break;
            default:        taiJi = (dzp == 6 || dzp == 9); break;
        }
        if (taiJi) sb.append("太极 ");

        // 月德（以月支为准）
        int yueZhi = r.gz[1] % 12;
        boolean yueDE = false;
        switch (yueZhi % 4) {
            case 0: yueDE = (tgp == 1); break; // 寅午戌月 → 丙
            case 1: yueDE = (tgp == 9); break; // 亥卯未月 → 壬
            case 2: yueDE = (tgp == 7); break; // 申子辰月 → 庚
            case 3: yueDE = (tgp == 3); break; // 巳酉丑月 → 甲
        }
        if (yueDE) sb.append("月德 ");

        // 文昌（以日主天干为准）
        boolean wenChang = false;
        switch (tri) {
            case 0: wenChang = (dzp == 2); break;
            case 1: case 2: wenChang = (dzp == 6 || dzp == 7); break;
            case 3: case 5: wenChang = (dzp == 9); break;
            case 4: case 6: wenChang = (dzp == 10); break;
            case 7: wenChang = (dzp == 0); break;
            case 8: wenChang = (dzp == 1); break;
            case 9: wenChang = (dzp == 3); break;
        }
        if (wenChang) sb.append("文昌 ");

        // 华盖（以年支为准）
        int nianZhi = r.gz[0] % 12;
        int huaGai;
        switch (nianZhi % 4) {
            case 0: huaGai = 8; break;  // 申子辰 → 辰
            case 1: huaGai = 5; break;  // 亥卯未 → 未
            case 2: huaGai = 2; break;  // 寅午戌 → 戌
            default: huaGai = 11; break; // 巳酉丑 → 丑
        }
        if (dzp == huaGai) sb.append("华盖 ");

        // 桃花（以年支或日支为准）
        int taoHuaBase = (pillar == 2) ? riZhi : nianZhi;
        int taoHua;
        switch (taoHuaBase % 4) {
            case 0: taoHua = 1; break;  // 申子辰 → 酉
            case 1: taoHua = 10; break; // 亥卯未 → 午
            case 2: taoHua = 7; break;  // 寅午戌 → 卯
            default: taoHua = 4; break; // 巳酉丑 → 子
        }
        if (pillar != 2 && dzp == taoHua) sb.append("桃花 ");

        // 驿马（以年支或日支为准）
        int yiMaBase = (pillar == 2) ? riZhi : nianZhi;
        int yiMa;
        switch (yiMaBase % 4) {
            case 0: yiMa = 6; break;  // 申子辰 → 寅
            case 1: yiMa = 3; break;  // 亥卯未 → 巳
            case 2: yiMa = 0; break;  // 寅午戌 → 申
            default: yiMa = 9; break; // 巳酉丑 → 亥
        }
        if (dzp == yiMa) sb.append("驿马 ");

        // 将星（以年支为准）
        int jiangXing;
        switch (nianZhi % 4) {
            case 0: jiangXing = 4; break;  // 申子辰 → 子
            case 1: jiangXing = 1; break;  // 亥卯未 → 卯
            case 2: jiangXing = 10; break; // 寅午戌 → 午
            default: jiangXing = 7; break; // 巳酉丑 → 酉
        }
        if (dzp == jiangXing) sb.append("将星 ");

        // 国印（日柱特殊神煞）
        if (pillar == 2) {
            if ((tri == 1 && dzp == 11) || (tri == 2 && dzp == 0)
             || (tri == 7 && dzp == 5)  || (tri == 8 && dzp == 6)
             || (tri == 9 && dzp == 8)  || (tri == 0 && dzp == 9)) {
                sb.append("国印 ");
            }
            // 天医
            int tianYiZhi = (yueZhi + 11) % 12; // 月支前一位
            if (dzp == tianYiZhi) sb.append("天医 ");
        }

        // 童子（时柱特殊神煞）
        if (pillar == 3) {
            // 童子：以日主天干判断
            int[] tongZiZhi = {1, 3, 5, 7, 9, 11, 1, 3, 5, 7};
            if (dzp == tongZiZhi[tri]) sb.append("童子 ");
        }

        // 大耗（月柱特殊神煞）
        if (pillar == 1) {
            // 大耗：以年支判断
            int daHao = (nianZhi + 1) % 12;
            if (dzp == daHao) sb.append("大耗 ");
        }

        return sb.toString().trim();
    }

    /**
     * 星座、生肖、文昌位、天乙贵人
     * 星座算法：基于月支推算（移植自原版 CalTwo）
     */
    private String formatXingZuoEtc(BaZiResult r) {
        StringBuilder sb = new StringBuilder();

        // 星座：以月柱地支推算（原版逻辑）
        // i24 = (月支 - 1)，若出生时刻 >= 中气则 +1，再 +8 取模12
        String[] xingZuo = {"白羊", "金牛", "双子", "巨蟹", "狮子", "处女",
                             "天秤", "天蝎", "人马", "摩羯", "宝瓶", "双鱼"};
        int i24 = (r.gz[1] % 12) - 1;
        if (i24 < 1) i24 += 12;
        if (r.dtmpsat >= r.zhongQi) i24++;
        int xzIdx = (i24 + 8) % 12;
        sb.append(String.format("☆星座：%s座。 生肖：%s。\n",
            xingZuo[xzIdx],
            BaZiConstants.SHENGXIAO[r.gz[0] % 12]));

        // 文昌位
        int tri = r.gz[2] % 10;
        String wenChangDir;
        switch (tri) {
            case 0: wenChangDir = "卯方、即东　方"; break;
            case 1: wenChangDir = "巳方、即东南方"; break;
            case 2: wenChangDir = "午方、即南　方"; break;
            case 3: wenChangDir = "申方、即西南方"; break;
            case 4: wenChangDir = "酉方、即西　方"; break;
            case 5: wenChangDir = "申方、即西南方"; break;
            case 6: wenChangDir = "酉方、即西　方"; break;
            case 7: wenChangDir = "亥方、即西北方"; break;
            case 8: wenChangDir = "子方、即北　方"; break;
            default: wenChangDir = "寅方、即东北方"; break;
        }
        sb.append(String.format("☆文昌位在%s。\n", wenChangDir));

        // 天乙贵人
        int g1, g2;
        switch (tri) {
            case 0: case 9: g1 = 4; g2 = 6; break;
            case 1: case 5: g1 = 2; g2 = 8; break;
            case 2: case 6: g1 = 1; g2 = 9; break;
            case 3: case 4: g1 = 0; g2 = 10; break;
            default:        g1 = 3; g2 = 7; break;
        }
        sb.append(String.format("☆天乙贵人：%s、%s。\n\n",
            BaZiConstants.DIZHI[g1], BaZiConstants.DIZHI[g2]));

        return sb.toString();
    }

    // ===== 测试入口 =====

    public static void main(String[] args) {
        BaZiEngine engine = new BaZiEngine();

        // 使用参考案例：1991年12月31日 13:10 男
//        String[] results = engine.getFullResult(1990, 1, 20, 8, 20, false, "李宣");
//        String[] results = engine.getFullResult(1991, 4, 1, 15, 30, true, "张云成");
//        String[] results = engine.getFullResult(1991, 12, 31, 13, 10, true, "Jeey");
//        String[] results = engine.getFullResult(1992, 8, 24, 21, 0, false, "陈杨");
//        String[] results = engine.getFullResult(1994, 11, 13, 12, 0, true, "巨人旗");
        String[] results = engine.getFullResult(1991, 3, 17, 16, 0, true, "刘伟");


        System.out.println(results[0]);  // 排盘结果

        System.out.println(results[1]);  // 运势报告
    }

    // ===== VO 构建方法 =====

    private BaZiPanVO buildPanVO(BaZiResult r) {
        BaZiPanVO vo = new BaZiPanVO();

        // 基本信息
        BaZiPanVO.BasicInfo basicInfo = new BaZiPanVO.BasicInfo();
        basicInfo.setName(r.name.isEmpty() ? null : r.name);
        basicInfo.setGender(r.isMale ? "男" : "女");
        basicInfo.setSolarYear(r.birthYear <= 0 ? r.birthYear - 1 : r.birthYear);
        basicInfo.setSolarMonth(r.birthMonth);
        basicInfo.setSolarDay(r.birthDay);
        basicInfo.setSolarHour(r.birthHour);
        basicInfo.setSolarMinute(r.birthMinute);
        basicInfo.setWeekDay(BaZiConstants.WEEK[DateCalc.getWeek(r.birthYear, r.birthMonth, r.birthDay)]);
        
        // 农历
        String nlYearStr = DateCalc.numToHz(r.nlYear > 0 ? r.nlYear : -r.nlYear);
        String nlMonthStr = r.nlMonth == 1 ? "正月" : DateCalc.numToHz(r.nlMonth) + "月";
        if (r.nlRun) nlMonthStr = "闰" + nlMonthStr;
        String nlDayStr = r.nlDay <= 10 ? "初" + DateCalc.numToHz(r.nlDay) : DateCalc.numToHz(r.nlDay) + "日";
        int shiZhi = ((r.birthHour % 2 == 1 ? (r.birthHour + 1) % 24 : r.birthHour) / 2 + 1) % 12;
        basicInfo.setLunarYear(nlYearStr);
        basicInfo.setLunarMonth(nlMonthStr);
        basicInfo.setLunarDay(nlDayStr);
        basicInfo.setLunarHourZhi(BaZiConstants.DIZHI[shiZhi]);
        basicInfo.setLunarMonthSize(r.yueTotal >= 30 ? "大月" : "小月");
        vo.setBasicInfo(basicInfo);

        // 四柱
        java.util.List<BaZiPanVO.PillarVO> pillars = new java.util.ArrayList<>();
        String[] pillarNames = {"年柱", "月柱", "日柱", "时柱"};
        for (int i = 0; i < 4; i++) {
            BaZiPanVO.PillarVO pillar = new BaZiPanVO.PillarVO();
            pillar.setPillarName(pillarNames[i]);
            pillar.setTianGan(BaZiConstants.TIANGAN[r.gz[i] % 10]);
            pillar.setDiZhi(BaZiConstants.DIZHI[r.gz[i] % 12]);
            pillar.setGanZhi(pillar.getTianGan() + pillar.getDiZhi());
            pillar.setNaYin(r.getNaYinName(i));
            pillar.setTianGanShiShen(BaZiConstants.SHISHEN[r.tian[i].shiShen]);
            pillar.setDiZhiShiShen(BaZiConstants.SHISHEN[r.di[i].shiShen]);
            
            // 地支藏干十神
            java.util.List<String> cangGanList = new java.util.ArrayList<>();
            if (r.di[i].shiShen2[0] >= 0) cangGanList.add(BaZiConstants.SHISHEN[r.di[i].shiShen2[0]]);
            if (r.di[i].shiShen2[1] >= 0) cangGanList.add(BaZiConstants.SHISHEN[r.di[i].shiShen2[1]]);
            if (r.di[i].shiShen2[2] >= 0) cangGanList.add(BaZiConstants.SHISHEN[r.di[i].shiShen2[2]]);
            pillar.setDiZhiCangGanShiShen(cangGanList);
            
            pillar.setG12(BaZiConstants.G12[r.di[i].wang]);
            pillar.setSanHe(r.di[i].sanHe >= 0);
            pillar.setSanHui(r.di[i].sanHui >= 0);
            pillar.setChong(r.di[i].chong >= 0);
            pillar.setHe(r.di[i].bHe);
            pillar.setXing(r.di[i].bXing);
            pillars.add(pillar);
        }
        vo.setPillars(pillars);

        // 节气
        int yueZhiIdx = (r.gz[1] % 12) - 1;
        if (yueZhiIdx < 1) yueZhiIdx += 12;
        String jq1Name = BaZiConstants.JIEQI[(yueZhiIdx - 1) * 2];
        String jq2Name = BaZiConstants.JIEQI[((yueZhiIdx) % 12) * 2];
        int[] jq1Date = DateCalc.fromJulian(r.dtmp1);
        int[] jq2Date = DateCalc.fromJulian(r.dtmpend);
        int jq1Year = jq1Date[0] <= 0 ? jq1Date[0] - 1 : jq1Date[0];
        int jq2Year = jq2Date[0] <= 0 ? jq2Date[0] - 1 : jq2Date[0];
        BaZiPanVO.JieQiVO jieQi = new BaZiPanVO.JieQiVO();
        jieQi.setPrevJieQiName(jq1Name);
        jieQi.setPrevJieQiDate(String.format("%d-%02d-%02d %02d:%02d", jq1Year, jq1Date[1], jq1Date[2], jq1Date[3], jq1Date[4]));
        jieQi.setNextJieQiName(jq2Name);
        jieQi.setNextJieQiDate(String.format("%d-%02d-%02d %02d:%02d", jq2Year, jq2Date[1], jq2Date[2], jq2Date[3], jq2Date[4]));
        vo.setJieQi(jieQi);

        // 大运
        int direction = (r.isMale && r.gz[0] % 2 == 1) || (!r.isMale && r.gz[0] % 2 == 0) ? 1 : -1;
        BaZiPanVO.DaYunSummary daYun = new BaZiPanVO.DaYunSummary();
        daYun.setDirection(direction > 0 ? "顺行" : "逆行");
        daYun.setStartAge(r.daYunAge);
        daYun.setStartYear(r.dynian + r.yunYear - 1);
        
        java.util.List<BaZiPanVO.DaYunStepVO> daYunSteps = new java.util.ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            int gz = ((r.gz[1] + i * direction) % 60 + 60) % 60;
            int ss = calc.qiuLq(r.gz[2], gz);
            BaZiPanVO.DaYunStepVO step = new BaZiPanVO.DaYunStepVO();
            step.setStep(i);
            step.setStepName(BaZiConstants.JIANPING_3[i - 1]);
            step.setGanZhi(BaZiConstants.TIANGAN[gz % 10] + BaZiConstants.DIZHI[gz % 12]);
            step.setTianGan(BaZiConstants.TIANGAN[gz % 10]);
            step.setDiZhi(BaZiConstants.DIZHI[gz % 12]);
            step.setShiShen(BaZiConstants.SHISHEN[ss]);
            int g12 = calc.jiSheng12(r.gz[2] % 10, gz % 12);
            step.setG12(BaZiConstants.G12[g12]);
            step.setStartAge(r.daYunAge + (i - 1) * 10);
            step.setStartYear(daYun.getStartYear() + (i - 1) * 10);
            daYunSteps.add(step);
        }
        daYun.setSteps(daYunSteps);
        
        // 流年表
        int startYear = daYun.getStartYear();
        java.util.List<java.util.List<String>> liuNianTable = new java.util.ArrayList<>();
        for (int row = 0; row < 10; row++) {
            java.util.List<String> rowList = new java.util.ArrayList<>();
            for (int col = 0; col < 8; col++) {
                int yr = startYear + row + col * 10;
                int gz = ((yr + 897 + 6000) % 60);
                rowList.add(BaZiConstants.TIANGAN[gz % 10] + BaZiConstants.DIZHI[gz % 12]);
            }
            liuNianTable.add(rowList);
        }
        daYun.setLiuNianTable(liuNianTable);
        daYun.setLiuNianStartYear(startYear);
        daYun.setLiuNianEndYear(startYear + 79);
        vo.setDaYun(daYun);

        // 胎元、命宫、身宫
        BaZiPanVO.SpecialStars specialStars = new BaZiPanVO.SpecialStars();
        specialStars.setTaiYuan(BaZiConstants.TIANGAN[r.tai % 10] + BaZiConstants.DIZHI[r.tai % 12]);
        specialStars.setTaiYuanNaYin(getNaYinName(r.tai));
        specialStars.setMingGong(BaZiConstants.TIANGAN[r.ming % 10] + BaZiConstants.DIZHI[r.ming % 12]);
        specialStars.setMingGongNaYin(getNaYinName(r.ming));
        specialStars.setShenGong(BaZiConstants.TIANGAN[r.shen % 10] + BaZiConstants.DIZHI[r.shen % 12]);
        specialStars.setShenGongNaYin(getNaYinName(r.shen));
        vo.setSpecialStars(specialStars);

        // 神煞
        BaZiPanVO.ShenShaVO shenSha = new BaZiPanVO.ShenShaVO();
        shenSha.setNianZhu(buildShenShaList(r, 0));
        shenSha.setYueZhu(buildShenShaList(r, 1));
        shenSha.setRiZhu(buildShenShaList(r, 2));
        shenSha.setShiZhu(buildShenShaList(r, 3));
        vo.setShenSha(shenSha);

        // 星座、生肖、文昌、天乙
        BaZiPanVO.XingZuoEtcVO xingZuoEtc = new BaZiPanVO.XingZuoEtcVO();
        String[] xingZuo = {"白羊", "金牛", "双子", "巨蟹", "狮子", "处女",
                             "天秤", "天蝎", "人马", "摩羯", "宝瓶", "双鱼"};
        int i24 = (r.gz[1] % 12) - 1;
        if (i24 < 1) i24 += 12;
        if (r.dtmpsat >= r.zhongQi) i24++;
        int xzIdx = (i24 + 8) % 12;
        xingZuoEtc.setXingZuo(xingZuo[xzIdx]);
        xingZuoEtc.setShengXiao(BaZiConstants.SHENGXIAO[r.gz[0] % 12]);
        
        int tri = r.gz[2] % 10;
        String wenChangDir;
        switch (tri) {
            case 0: wenChangDir = "卯方、即东方"; break;
            case 1: wenChangDir = "巳方、即东南方"; break;
            case 2: wenChangDir = "午方、即南方"; break;
            case 3: wenChangDir = "申方、即西南方"; break;
            case 4: wenChangDir = "酉方、即西方"; break;
            case 5: wenChangDir = "申方、即西南方"; break;
            case 6: wenChangDir = "酉方、即西方"; break;
            case 7: wenChangDir = "亥方、即西北方"; break;
            case 8: wenChangDir = "子方、即北方"; break;
            default: wenChangDir = "寅方、即东北方"; break;
        }
        xingZuoEtc.setWenChangDir(wenChangDir);
        
        int g1, g2;
        switch (tri) {
            case 0: case 9: g1 = 4; g2 = 6; break;
            case 1: case 5: g1 = 2; g2 = 8; break;
            case 2: case 6: g1 = 1; g2 = 9; break;
            case 3: case 4: g1 = 0; g2 = 10; break;
            default:        g1 = 3; g2 = 7; break;
        }
        xingZuoEtc.setTianYi1(BaZiConstants.DIZHI[g1]);
        xingZuoEtc.setTianYi2(BaZiConstants.DIZHI[g2]);
        vo.setXingZuoEtc(xingZuoEtc);

        // 五行力量
        BaZiPanVO.WuXingVO wuXing = new BaZiPanVO.WuXingVO();
        int total = 0;
        for (short v : r.wuXingJ) total += v;
        wuXing.setShui(r.wuXingJ[0]);
        wuXing.setShuiPct(total > 0 ? r.wuXingJ[0] * 100.0 / total : 0);
        wuXing.setMu(r.wuXingJ[1]);
        wuXing.setMuPct(total > 0 ? r.wuXingJ[1] * 100.0 / total : 0);
        wuXing.setHuo(r.wuXingJ[2]);
        wuXing.setHuoPct(total > 0 ? r.wuXingJ[2] * 100.0 / total : 0);
        wuXing.setTu(r.wuXingJ[3]);
        wuXing.setTuPct(total > 0 ? r.wuXingJ[3] * 100.0 / total : 0);
        wuXing.setJin(r.wuXingJ[4]);
        wuXing.setJinPct(total > 0 ? r.wuXingJ[4] * 100.0 / total : 0);
        
        int shengZhu = r.shiShenJ[0] + r.shiShenJ[4];
        int keXie    = r.shiShenJ[1] + r.shiShenJ[2] + r.shiShenJ[3];
        int szTotal  = shengZhu + keXie;
        wuXing.setShengZhu(shengZhu);
        wuXing.setShengZhuPct(szTotal > 0 ? shengZhu * 100.0 / szTotal : 0);
        wuXing.setKeXie(keXie);
        wuXing.setKeXiePct(szTotal > 0 ? keXie * 100.0 / szTotal : 0);
        wuXing.setYinScore(r.yyScore[0]);
        wuXing.setYinPct((r.yyScore[0] + r.yyScore[1]) > 0 ? r.yyScore[0] * 100.0 / (r.yyScore[0] + r.yyScore[1]) : 0);
        wuXing.setYangScore(r.yyScore[1]);
        wuXing.setYangPct((r.yyScore[0] + r.yyScore[1]) > 0 ? r.yyScore[1] * 100.0 / (r.yyScore[0] + r.yyScore[1]) : 0);
        vo.setWuXing(wuXing);

        // 日元旺衰 + 用神
        BaZiPanVO.RiYuanYongShenVO riYuanYongShen = new BaZiPanVO.RiYuanYongShenVO();
        riYuanYongShen.setScore(r.score);
        riYuanYongShen.setRiYuanDesc(getRiYuanDesc(r.riYuan));
        riYuanYongShen.setXiYong1(BaZiConstants.WUXING[r.wsYong[0][0]]);
        if (r.wsYong[0].length > 1) riYuanYongShen.setXiYong2(BaZiConstants.WUXING[r.wsYong[0][1]]);
        if (r.wsYong[0].length > 2) riYuanYongShen.setJiChou1(BaZiConstants.WUXING[r.wsYong[0][2]]);
        if (r.wsYong[0].length > 3) riYuanYongShen.setJiChou2(BaZiConstants.WUXING[r.wsYong[0][3]]);
        vo.setRiYuanYongShen(riYuanYongShen);

        return vo;
    }

    private java.util.List<String> buildShenShaList(BaZiResult r, int pillar) {
        java.util.List<String> list = new java.util.ArrayList<>();
        for (byte sha : r.shenSha[pillar]) {
            if (sha > 0) {
                // 这里需要从 shenSha 数组映射到名称，简化处理
                // 实际应该有完整的神煞名称数组
            }
        }
        return list;
    }

    private BaZiYunVO buildYunVO(BaZiResult r) {
        // 运势 VO 构建较复杂，需要调用 YunQiCalc
        BaZiYunVO vo = new BaZiYunVO();
        // YunQiCalc yunCalc = new YunQiCalc(r);
        // 这里可以从 YunQiCalc 中提取数据，或者直接返回文本报告
        // 为了简化，暂时返回空 VO，后续可扩展
        return vo;
    }
}