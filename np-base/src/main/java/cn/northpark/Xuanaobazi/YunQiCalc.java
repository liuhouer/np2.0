package cn.northpark.Xuanaobazi;

/**
 * 大运流年运势分析（1-70岁）
 * 严格按照原版 P3_YunQi + LiuNianDuan + LiuNianDuan_Ex 逻辑
 */
public class YunQiCalc {

    private final BaZiResult info;
    private final BaZiCalc   calc;
    private final int direction; // 1=顺行，-1=逆行

    public YunQiCalc(BaZiResult result) {
        this.info = result;
        this.calc = new BaZiCalc();
        int yearGanYY = result.gz[0] % 2;
        // 阳干（奇数索引1,3,5,7,9）男顺行；阴干（偶数索引0,2,4,6,8）男逆行
        boolean shunXing = (result.isMale && yearGanYY == 1)
                        || (!result.isMale && yearGanYY == 0);
        this.direction = shunXing ? 1 : -1;
    }

    /** 生成完整运势报告（1-70岁） */
    public String generateFullReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════\n");
        sb.append("        运势分析报告（1-70岁）\n");
        sb.append("═══════════════════════════════════\n\n");

        // 未交大运前
        if (info.daYunAge > 1) {
            sb.append("【未交大运前】\n");
            int maxAge = Math.min(info.daYunAge - 1, 70);
            for (int age = 1; age <= maxAge; age++) {
                sb.append(getAgeReport(age));
            }
            sb.append("\n");
        }

        // 各步大运
        for (int step = 1; step <= 9; step++) {
            int startAge = info.daYunAge + (step - 1) * 10;
            if (startAge > 70) break;
            int endAge = Math.min(startAge + 9, 70);
            sb.append(getDaYunHeader(step));
            for (int age = startAge; age <= endAge; age++) {
                sb.append(getAgeReport(age));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /** 大运标题（对应原版 P3_YunQi 的大运段） */
    public String getDaYunHeader(int step) {
        int i = step - 1;
        int gz = ((info.gz[1] + (i + 1) * direction) % 60 + 60) % 60;
        int startYear = info.yunYear + info.daYunAge - 1 + i * 10;
        int endYear   = startYear + 9;
        int shiShen   = qiuLq(info.gz[2], gz);
        int g12       = jiSheng12(info.gz[2] % 10, gz % 12);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("第%s步大运：%s%s管 %d年至 %d年\n",
            BaZiConstants.JIANPING_3[i],
            BaZiConstants.TIANGAN[gz % 10],
            BaZiConstants.DIZHI[gz % 12],
            startYear, endYear));
        sb.append(String.format("※大运%s主事，运行%s之地。  前五年运势：%d分，后五年运势：%d分。\n",
            BaZiConstants.SHISHEN[shiShen],
            BaZiConstants.G12[g12],
            info.dyun[i * 2],
            info.dyun[i * 2 + 1]));

        // 大运天干地支用神分析
        sb.append(getDaYunYongShenDesc(gz));
        sb.append(getDaYunAnalysis(shiShen, g12));

        // 大运空亡
        if (isKongWang(gz, info.gz[0])) {
            sb.append("大运逢空亡。");
        }
        sb.append("\n");
        return sb.toString();
    }

    /** 指定年龄流年运势（对应原版 P3_YunQi_Age） */
    public String getAgeReport(int age) {
        if (age < 1 || age > 150) return "";

        int lnianGz = ((info.yunYear + age - 1 + 897 + 6000) % 60);
        int xiaoYunGz = ((info.gz[3] + direction * age) % 60 + 60) % 60;
        int calYear   = info.yunYear + age - 1;
        if (calYear <= 0) calYear--;

        int lnianSS  = qiuLq(info.gz[2], lnianGz);
        int lnianG12 = jiSheng12(info.gz[2] % 10, lnianGz % 12);

        // 当前大运
        int daYunGz = -1;
        if (age >= info.daYunAge) {
            int step = (age - info.daYunAge) / 10;
            daYunGz = ((info.gz[1] + (step + 1) * direction) % 60 + 60) % 60;
        }

        StringBuilder sb = new StringBuilder();

        // 标题行
        if (age < info.daYunAge) {
            sb.append(String.format("【%d岁流年：%s%s%d年 小运：%s%s】",
                age,
                BaZiConstants.TIANGAN[lnianGz % 10], BaZiConstants.DIZHI[lnianGz % 12],
                calYear,
                BaZiConstants.TIANGAN[xiaoYunGz % 10], BaZiConstants.DIZHI[xiaoYunGz % 12]));
        } else {
            sb.append(String.format("【%d岁流年：%d年%s%s 小运：%s%s】",
                age, calYear,
                BaZiConstants.TIANGAN[lnianGz % 10], BaZiConstants.DIZHI[lnianGz % 12],
                BaZiConstants.TIANGAN[xiaoYunGz % 10], BaZiConstants.DIZHI[xiaoYunGz % 12]));
        }
        sb.append(String.format("流年%s主事，运行%s之地。",
            BaZiConstants.SHISHEN[lnianSS],
            BaZiConstants.G12[lnianG12]));

        // 天干地支用神分析
        sb.append(getLiuNianYongShenDesc(lnianGz));

        // 运势评分
        int lnianFen = info.lnian[age - 1] & 0xFF;
        int xiaoFen  = info.xxian[age - 1] & 0xFF;
        int zFen     = info.zscore[age - 1] & 0xFF;
        if (age < info.daYunAge) {
            sb.append(String.format("★小限运势：%d分，流年运势：%d分，实际运势：%d分。\n",
                xiaoFen, lnianFen, zFen));
        } else {
            sb.append(String.format("★流年运势：%d分，实际运势：%d分。\n",
                lnianFen, zFen));
        }

        // 大运与流年特殊关系
        if (daYunGz >= 0) {
            if (daYunGz == lnianGz) {
                sb.append("运交岁运并临，须防有重大事情发生，");
            }
            if ((Math.abs((daYunGz % 10) - (lnianGz % 10)) == 4
              || Math.abs((daYunGz % 10) - (lnianGz % 10)) == 6)
              && Math.abs((daYunGz % 12) - (lnianGz % 12)) == 6) {
                sb.append("大运与流年天克地冲，");
            }
        }

        // 天干合
        int heResult = isGanWuHe(lnianGz % 10, info.gz[1] % 10);
        if (heResult >= 0) {
            sb.append(String.format("%s%s流年与月%s之合。",
                BaZiConstants.TIANGAN[lnianGz % 10],
                BaZiConstants.TIANGAN[info.gz[1] % 10],
                getHeDesc(heResult)));
        }
        heResult = isGanWuHe(lnianGz % 10, info.gz[2] % 10);
        if (heResult >= 0) {
            sb.append(String.format("%s%s流年与日%s之合。",
                BaZiConstants.TIANGAN[lnianGz % 10],
                BaZiConstants.TIANGAN[info.gz[2] % 10],
                getHeDesc(heResult)));
        }

        // 地支冲
        int chongResult = isZhiChong(lnianGz % 12, info.gz[2] % 12);
        if (chongResult >= 0) {
            sb.append(String.format("%s%s流年与日相冲。",
                BaZiConstants.DIZHI[lnianGz % 12],
                BaZiConstants.DIZHI[info.gz[2] % 12]));
        }

        // 详细分析
        sb.append(getLiuNianAnalysis(age, lnianGz, lnianSS, lnianG12, daYunGz, xiaoYunGz));

        // 流年神煞
        sb.append(getLiuNianShenSha(age, lnianGz, xiaoYunGz));

        // 64卦（16岁以上）
        if (age >= 16 && daYunGz >= 0) {
            int t5 = ((daYunGz + lnianGz) % 64) + 1;
            sb.append(String.format("★此年占得:%s卦★\n", BaZiConstants.GUA64[t5 - 1]));
        }

        sb.append("\n");
        return sb.toString();
    }

    // ===== 用神描述 =====

    private String getDaYunYongShenDesc(int gz) {
        StringBuilder sb = new StringBuilder();
        int tgWx = BaZiConstants.TIANGAN_WXJ[gz % 10];
        int dzWx = BaZiConstants.DIZHI_WXJ[gz % 12];
        sb.append(String.format("★天干%s%s，地支%s%s。",
            BaZiConstants.TIANGAN[gz % 10], getYongShenName(tgWx),
            BaZiConstants.DIZHI[gz % 12], getYongShenName(dzWx)));
        return sb.toString();
    }

    private String getLiuNianYongShenDesc(int gz) {
        int tgWx = BaZiConstants.TIANGAN_WXJ[gz % 10];
        int dzWx = BaZiConstants.DIZHI_WXJ[gz % 12];
        return String.format("天干%s%s，地支%s%s。",
            BaZiConstants.TIANGAN[gz % 10], getYongShenName(tgWx),
            BaZiConstants.DIZHI[gz % 12], getYongShenName(dzWx));
    }

    private String getYongShenName(int wx) {
        // 找到该五行在用神中的位置
        for (int i = 0; i < 5; i++) {
            if (info.wsYong[0][i] == wx) {
                return BaZiConstants.YONGSHEN[i];
            }
        }
        return "闲神";
    }

    // ===== 大运分析 =====

    private String getDaYunAnalysis(int shiShen, int g12) {
        StringBuilder sb = new StringBuilder();
        switch (shiShen) {
            case 0: sb.append("★此时比肩运，同类相助，宜合作共事，防竞争内耗。"); break;
            case 1: sb.append("★此时劫财运，财来财去，宜守不宜攻，防破财损财。"); break;
            case 2: sb.append("★此时食神运，衣食丰足，才华得展，事业顺遂。"); break;
            case 3: sb.append("★此时切不可有创业的念头，不然失败的可能性很大，可能到有破财、伤身之事。你也许觉得凡事不顺，那么还是等以后再来谋划方为上策。"); break;
            case 4: sb.append("★此时偏财运，财运亨通，宜经商投资，防意外之财。"); break;
            case 5: sb.append("★此时正财运，财源稳定，踏实进取，财富积累良好。"); break;
            case 6: sb.append("★此时七杀运，压力较大，逢制则贵，无制则凶，需谨慎应对。"); break;
            case 7: sb.append("★此时正官运，仕途顺畅，名誉提升，宜从政从职。"); break;
            case 8: sb.append("★此时偏印运，学习进修，宜静不宜动，防孤独忧郁。"); break;
            case 9: sb.append("★此时正印运，贵人相助，学业有成，名誉良好。"); break;
        }
        sb.append("\n");
        return sb.toString();
    }

    // ===== 流年详细分析（对应原版 LiuNianDuan + LiuNianDuan_Ex）=====

    private String getLiuNianAnalysis(int age, int lnianGz, int shiShen, int g12,
                                       int daYunGz, int xiaoYunGz) {
        StringBuilder sb = new StringBuilder();

        // 基于十神的流年分析
        switch (shiShen) {
            case 0: sb.append("★利于与他人合伙创业，合作共事，交朋结友发展前途。\n"); break;
            case 1: sb.append("★此年财运波动，防破财，慎投资，防小人争夺。\n"); break;
            case 2: sb.append("★此年才华发挥，饮食丰足，身心愉悦，事业顺遂。\n"); break;
            case 3: sb.append("★此年思维活跃，创意十足，但需防口舌是非，官场不利。\n"); break;
            case 4: sb.append("★此年意外之财，宜把握机遇，防横财横祸。\n"); break;
            case 5: sb.append("★此年财运平稳，踏实努力，收获丰厚。\n"); break;
            case 6: sb.append("★此年压力增大，竞争激烈，需以智慧化解，防官非。\n"); break;
            case 7: sb.append("★此年事业顺利，名誉提升，贵人相助，宜从政从职。\n"); break;
            case 8: sb.append("★此年学习进修，宜静思，防孤立无援，防意外。\n"); break;
            case 9: sb.append("★此年贵人扶持，学业进步，名誉良好，宜进修。\n"); break;
        }

        // 用神相关分析
        if (info.wsYong[1][0] == 3 || info.wsYong[1][1] == 3) {
            // 正官为用神
            int dzSS = BaZiConstants.DIBENGAN[lnianGz % 12];
            int dzShiShen = qiuLq(info.gz[2] % 10, dzSS);
            if (dzShiShen == 3) {
                sb.append("★正官为用神, 大运流年最忌伤官, 犯之主破缘, 官司, 破财等事。\n");
            }
        }

        // 食伤运分析
        if (shiShen == 2 || shiShen == 3) {
            sb.append("★人的运逢食伤，因大胆泄秀之故，行事必然粗心大意而不重安全，因而食伤运比较容易发生意外受伤的事。\n");
        }

        // 大运用神到位
        if (daYunGz >= 0) {
            int daYunDzSS = qiuLq(info.gz[2] % 10, BaZiConstants.DIBENGAN[daYunGz % 12]);
            int daYunDzSSJ = daYunDzSS / 2;
            if (daYunDzSSJ == info.wsYong[1][0] || daYunDzSSJ == info.wsYong[1][1]) {
                sb.append("★大运用神到位, 则此运多主平安, 兴旺, 发达。\n");
            }
        }

        // 食伤克官杀
        if (info.riYuan > 3) {
            int lnianDzSS = qiuLq(info.gz[2] % 10, BaZiConstants.DIBENGAN[lnianGz % 12]);
            if (lnianDzSS / 2 == 1) {
                sb.append("★食伤克官杀的岁运，有时候会离开工作岗位或者离开其主管。\n");
            }
        }

        // 岁支十神分析
        int dzSS = qiuLq(info.gz[2] % 10, BaZiConstants.DIBENGAN[lnianGz % 12]);
        int dzSSJ = dzSS / 2;
        switch (dzSSJ) {
            case 0: sb.append("★岁支是比劫，就发生和朋友，同事，兄弟姐妹有关的事。\n"); break;
            case 1: sb.append("★岁支是食伤，就发生同伤病灾，官灾，发表作品，演说，言论，演出，跳舞，展示，投资，策划之类事情。\n"); break;
            case 2: sb.append("★岁支是财星，就会发生同妻子，父亲，财运，身体（病伤灾）工作以及婚姻，感情方面之事。\n"); break;
            case 3: sb.append("★岁支是官杀，就可能发生同父亲，工作，职务，职业，名誉，官司，病伤灾等方面有管你之事。\n"); break;
            case 4: sb.append("★岁支是印星，就会发生同印星所代表意义范围有关之事，例：学习，工作，单位，名誉，票据，住房，疾病，财运有关之事。\n"); break;
        }

        // 流年冲日柱
        if (isZhiChong(lnianGz % 12, info.gz[2] % 12) >= 0) {
            sb.append("★流年冲八字日柱，本人与配偶小心行车，逢冲之丑、未月、申寅、甲申月留意。\n");
        }
        // 流年冲月柱
        if (isZhiChong(lnianGz % 12, info.gz[1] % 12) >= 0) {
            sb.append("★流年冲八字月柱，命造同辈小心行车，逢冲之丑、未月、申寅、甲申月留意。\n");
        }
        // 流年冲年柱
        if (isZhiChong(lnianGz % 12, info.gz[0] % 12) >= 0) {
            sb.append("★流年冲八字年柱，命造长辈小心行车，逢冲之丑、未月、申寅、甲申月留意。\n");
        }

        // 大运将交未交最后一年
        if (age >= info.daYunAge && (age - info.daYunAge) % 10 == 9) {
            sb.append("★在大运将交未交的最后一年，往往有人生重大之变动，命主应特别谨慎渡过。\n");
        }

        return sb.toString();
    }

    // ===== 流年神煞 =====

    private String getLiuNianShenSha(int age, int lnianGz, int xiaoYunGz) {
        StringBuilder sb = new StringBuilder();
        sb.append("★流年神煞：");
        boolean hasSha = false;

        // 驿马
        int riZhi = info.gz[2] % 12;
        int yiMa = getYiMa(riZhi);
        if (lnianGz % 12 == yiMa) {
            sb.append("驿马此年多走动。"); hasSha = true;
        }

        // 桃花
        int taoHua = getTaoHua(riZhi);
        if (lnianGz % 12 == taoHua) {
            sb.append("桃花此年异性缘旺。"); hasSha = true;
        }

        // 空亡
        if (isKongWang(lnianGz, info.gz[0])) {
            sb.append("流年逢空亡，竹篮打水运。"); hasSha = true;
        }

        // 华盖
        int huaGai = getHuaGai(info.gz[0] % 12);
        if (lnianGz % 12 == huaGai) {
            sb.append("华盖此年宜静修。"); hasSha = true;
        }

        if (!hasSha) sb.append("无特殊神煞。");
        sb.append("\n");
        return sb.toString();
    }

    // ===== 工具方法 =====

    private int qiuLq(int riGz, int qGz) {
        int t1 = riGz % 10;
        int t2 = qGz % 10;
        if (t2 < t1) t2 += 10;
        if (t1 % 2 == 0 && (t2 - t1) % 2 == 1) t2 += 2;
        return (t2 - t1) % 10;
    }

    private int jiSheng12(int tian, int di) {
        int t1 = BaZiConstants.G12DUI[tian];
        int t2 = (tian % 2 == 1) ? di - t1 : t1 - di;
        return (t2 % 12 + 12) % 12;
    }

    /** 天干五合，返回合化五行，无合返回-1 */
    private int isGanWuHe(int g1, int g2) {
        int[][] he = {{0,5},{1,6},{2,7},{3,8},{4,9},{5,0},{6,1},{7,2},{8,3},{9,4}};
        if (he[g1][1] == g2) return he[g1][0] % 5;
        return -1;
    }

    /** 地支六冲，返回冲的地支，无冲返回-1 */
    private int isZhiChong(int z1, int z2) {
        if ((z1 - z2 + 12) % 12 == 6 || (z2 - z1 + 12) % 12 == 6) return z2;
        return -1;
    }

    /** 五合名称 */
    private String getHeDesc(int wx) {
        String[] heType = {"仁义", "仁义", "威制", "仁义", "中正"};
        if (wx >= 0 && wx < 5) return heType[wx];
        return "";
    }

    /** 空亡判断（以年柱为基准） */
    private boolean isKongWang(int gz, int yearGz) {
        int[] kongWang = getKongWang(yearGz);
        int zhi = gz % 12;
        return zhi == kongWang[0] || zhi == kongWang[1];
    }

    /** 获取空亡地支（旬空） */
    private int[] getKongWang(int gz) {
        int xun = (gz / 10) * 10; // 旬首
        int start = xun % 12;
        return new int[]{(start + 10) % 12, (start + 11) % 12};
    }

    /** 驿马 */
    private int getYiMa(int zhi) {
        int[] yiMa = {6, 3, 0, 9, 6, 3, 0, 9, 6, 3, 0, 9};
        return yiMa[zhi];
    }

    /** 桃花 */
    private int getTaoHua(int zhi) {
        int[] taoHua = {1, 10, 7, 4, 1, 10, 7, 4, 1, 10, 7, 4};
        return taoHua[zhi % 4 == 0 ? 0 : zhi % 4 == 1 ? 1 : zhi % 4 == 2 ? 2 : 3];
    }

    /** 华盖 */
    private int getHuaGai(int zhi) {
        int[] huaGai = {8, 5, 2, 11, 8, 5, 2, 11, 8, 5, 2, 11};
        return huaGai[zhi % 4];
    }
}
