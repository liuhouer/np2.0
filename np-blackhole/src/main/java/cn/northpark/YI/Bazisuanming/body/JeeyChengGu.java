package cn.northpark.YI.Bazisuanming.body;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bruce
 * @date 2024年01月19日 13:56:17
 */
public class JeeyChengGu {
    public static final Map<String, Double> yearMap;
    public static final Map<String, Double> monthMap;
    public static final Map<String, Double> dayMap;
    public static final Map<String, Double> hourMap;

    public static final Map<String, String> mansongMap;
    public static final Map<String, String> womansongMap;

    static {
        // 初始化年 map
        Map<String, Double> tempYearMap = new HashMap<>();
        tempYearMap.put("甲子", 1.2);
        tempYearMap.put("乙丑", 0.9);
        tempYearMap.put("丙寅", 0.6);
        tempYearMap.put("丁卯", 0.7);
        tempYearMap.put("戊辰", 1.2);
        tempYearMap.put("己巳", 0.5);
        tempYearMap.put("庚午", 0.9);
        tempYearMap.put("辛未", 0.8);
        tempYearMap.put("壬申", 0.7);
        tempYearMap.put("癸酉", 0.8);
        tempYearMap.put("甲戌", 1.5);
        tempYearMap.put("乙亥", 0.9);

        tempYearMap.put("丙子", 1.6);
        tempYearMap.put("丁丑", 0.8);
        tempYearMap.put("戊寅", 0.8);
        tempYearMap.put("己卯", 1.9);
        tempYearMap.put("庚辰", 1.2);
        tempYearMap.put("辛巳", 0.6);
        tempYearMap.put("壬午", 0.8);
        tempYearMap.put("癸未", 0.7);
        tempYearMap.put("甲申", 0.5);
        tempYearMap.put("乙酉", 1.5);
        tempYearMap.put("丙戌", 0.6);
        tempYearMap.put("丁亥", 1.6);

        tempYearMap.put("戊子", 1.5);
        tempYearMap.put("己丑", 0.7);
        tempYearMap.put("庚寅", 0.9);
        tempYearMap.put("辛卯", 1.2);
        tempYearMap.put("壬辰", 1.0);
        tempYearMap.put("癸巳", 0.7);
        tempYearMap.put("甲午", 1.5);
        tempYearMap.put("乙未", 0.6);
        tempYearMap.put("丙申", 0.5);
        tempYearMap.put("丁酉", 1.4);
        tempYearMap.put("戊戌", 1.4);
        tempYearMap.put("己亥", 0.9);

        tempYearMap.put("庚子", 0.7);
        tempYearMap.put("辛丑", 0.7);
        tempYearMap.put("壬寅", 0.9);
        tempYearMap.put("癸卯", 1.2);
        tempYearMap.put("甲辰", 0.8);
        tempYearMap.put("乙巳", 0.7);
        tempYearMap.put("丙午", 1.3);
        tempYearMap.put("丁未", 0.5);
        tempYearMap.put("戊申", 1.4);
        tempYearMap.put("己酉", 0.5);
        tempYearMap.put("庚戌", 0.9);
        tempYearMap.put("辛亥", 1.7);

        tempYearMap.put("壬子", 0.5);
        tempYearMap.put("癸丑", 0.7);
        tempYearMap.put("甲寅", 1.2);
        tempYearMap.put("乙卯", 0.8);
        tempYearMap.put("丙辰", 0.8);
        tempYearMap.put("丁巳", 0.6);
        tempYearMap.put("戊午", 1.9);
        tempYearMap.put("己未", 0.6);
        tempYearMap.put("庚申", 0.8);
        tempYearMap.put("辛酉", 1.6);
        tempYearMap.put("壬戌", 1.0);
        tempYearMap.put("癸亥", 0.6);

        yearMap = Collections.unmodifiableMap(tempYearMap);

        // 初始化月 map
        Map<String, Double> tempMonthMap = new HashMap<>();
        tempMonthMap.put("一月", 0.6);
        tempMonthMap.put("二月", 0.7);
        tempMonthMap.put("三月", 1.8);
        tempMonthMap.put("四月", 0.9);
        tempMonthMap.put("五月", 0.5);
        tempMonthMap.put("六月", 1.6);
        tempMonthMap.put("七月", 0.9);
        tempMonthMap.put("八月", 1.5);
        tempMonthMap.put("九月", 1.8);
        tempMonthMap.put("十月", 0.8);
        tempMonthMap.put("十一月", 0.9);
        tempMonthMap.put("十二月", 0.5);

        monthMap = Collections.unmodifiableMap(tempMonthMap);

        // 初始化日 map
        Map<String, Double> tempDayMap = new HashMap<>();
        tempDayMap.put("初一", 0.5);
        tempDayMap.put("初二", 1.0);
        tempDayMap.put("初三", 0.8);
        tempDayMap.put("初四", 1.5);
        tempDayMap.put("初五", 1.6);
        tempDayMap.put("初六", 1.5);
        tempDayMap.put("初七", 0.8);
        tempDayMap.put("初八", 1.6);
        tempDayMap.put("初九", 0.8);
        tempDayMap.put("初十", 1.6);

        tempDayMap.put("十一", 0.9);
        tempDayMap.put("十二", 1.7);
        tempDayMap.put("十三", 0.8);
        tempDayMap.put("十四", 1.7);
        tempDayMap.put("十五", 1.0);
        tempDayMap.put("十六", 0.8);
        tempDayMap.put("十七", 0.9);
        tempDayMap.put("十八", 1.8);
        tempDayMap.put("十九", 0.5);
        tempDayMap.put("二十", 1.0);

        tempDayMap.put("廿一", 1.0);
        tempDayMap.put("廿二", 0.9);
        tempDayMap.put("廿三", 0.8);
        tempDayMap.put("廿四", 0.9);
        tempDayMap.put("廿五", 1.5);
        tempDayMap.put("廿六", 1.8);
        tempDayMap.put("廿七", 0.7);
        tempDayMap.put("廿八", 0.8);
        tempDayMap.put("廿九", 1.6);
        tempDayMap.put("卅", 0.6);

        dayMap = Collections.unmodifiableMap(tempDayMap);

        // 初始化时 map
        Map<String, Double> tempHourMap = new HashMap<>();
        tempHourMap.put("子", 1.6);
        tempHourMap.put("丑", 0.6);
        tempHourMap.put("寅", 0.7);
        tempHourMap.put("卯", 1.0);
        tempHourMap.put("辰", 0.9);
        tempHourMap.put("巳", 1.6);
        tempHourMap.put("午", 1.0);
        tempHourMap.put("未", 0.8);
        tempHourMap.put("申", 0.8);
        tempHourMap.put("酉", 0.9);
        tempHourMap.put("戌", 0.6);
        tempHourMap.put("亥", 0.6);

        hourMap = Collections.unmodifiableMap(tempHourMap);

        //初始化 mansongMap
        Map<String, String> tempMansongMap = new HashMap<>();
        tempMansongMap.put("二两一","此命非业谓大凶,平生灾害事盈重。凶祸频临陷逆境,终世困苦事不成。");
        tempMansongMap.put("二两二","身寒骨冷苦伶们,此命推来真气人。碌碌巴巴无度日,终年打拱过平年。");
        tempMansongMap.put("二两三","此命推来骨自轻,求谋作事事难成。妻儿兄弟应难许,别处他乡作散人。");
        tempMansongMap.put("二两四","此命推来福禄无,门庭困苦总难营。六亲骨肉皆无靠,流到他乡作老翁。");
        tempMansongMap.put("二两五","此命推来祖业微,门庭营度似稀奇。六亲骨肉如冰炭,一生勤劳自把持。");
        tempMansongMap.put("二两六","平生衣禄苦中求,独自经营事不休。离祖出门直早计,晚来衣禄自无忧。");
        tempMansongMap.put("二两七","一生作事少商量,难靠祖宗作主张。独马单枪空做去,早来晚岁部无长。");
        tempMansongMap.put("二两八","三生作事似飘蓬,祖宗产业在梦中。若不过房并改姓,也当移徙两三通。");
        tempMansongMap.put("二两九","初年运限未曾享,纵有功名在后底。须过四旬绕可上,移居改姓始为良。");
        tempMansongMap.put("三两","劳劳碌碌苦中求,东走西奔何日休。若使终身勤与俭,老来稍可免忧愁。");
        tempMansongMap.put("三两一","忙忙碌碌苦中求,何日云开见日头。难得祖基家可立,中年衣食渐无忧。");
        tempMansongMap.put("三两二","初年运蹇事难谋,渐有财源如水流。到得中年衣食旺,那时名利一齐来。");
        tempMansongMap.put("三两三","早年做事事难成,百计徒劳枉费心。半世自如流水去,后来运到得黄金。");
        tempMansongMap.put("三两四","此命福气果如何,僧道门中衣禄多。离祖出家方得妙,终朝拜佛念孺陀。");
        tempMansongMap.put("三两五","平生福量不周全,祖业根基觉少传。营业生涯宜守旧,时来衣食胜从前。");
        tempMansongMap.put("三两六","不须劳碌过平生,独自成家福不轻。早有福星常照命,任君行去百般成。");
        tempMansongMap.put("三两七","此命般般事不成,弟兄少力自孤成。虽然祖业须微有,来得明时去得明。");
        tempMansongMap.put("三两八","一生骨肉最清高,早入黄门姓名标。待看看将三十六,蓝袍脱去换红袍。");
        tempMansongMap.put("三两九","此命终身运不穷,劳劳作事尽皆空。苦心竭力成家计,到得那时在梦中。");
        tempMansongMap.put("四两","生平衣禄是绵长,件件心中自主张。前面风霜多受过,后来必定享安康。");
        tempMansongMap.put("四两一","此命推来事不同,为人能干略凡庸。中年还有逍遥福,不比前年运未通。");
        tempMansongMap.put("四两二","得宽怀处且宽怀,何用双眉皱不开。若使中年命运济,那时名利一齐来。");
        tempMansongMap.put("四两三","为人心性最聪明,作事轩昂近贵人。衣禄一生天数定,不须劳碌是丰享。");
        tempMansongMap.put("四两四","来事由天莫苦求,须知福禄胜前途。当年财帛难如意,晚景欣然便不忧。");
        tempMansongMap.put("四两五","名利推来竟若何,前途辛苦后奔波。命中难养男与女,骨肉扶持也不多。");
        tempMansongMap.put("四两六","东西南北尽皆空,出姓移名更觉隆。衣禄无亏天数定,中年晚景一般同。");
        tempMansongMap.put("四两七","此命推来旺末年,妻荣子贵自怡然。平生原有滔滔福,可有财源如水源。");
        tempMansongMap.put("四两八","动年运道未曾享,若是踌距再不兴。兄弟六亲皆无靠,一身事业晚年成。");
        tempMansongMap.put("四两九","此命推来福不轻,自成自立耀门庭。从来富贵人亲近,使姆差奴过一生。");
        tempMansongMap.put("五两","为名为利终日劳,中年福禄也多遭。老来是有财星照,不比前番日下高。");
        tempMansongMap.put("五两一","二世荣华事事通,不须劳碌自享丰。弟兄叔侄皆如意,家业成时福禄宏。");
        tempMansongMap.put("五两二","二世享通事事能,不须劳思自然能。家族欣然心皆好,家业丰享自称心。");
        tempMansongMap.put("五两三","此格推来气像真,兴家发达在其中。一生福禄安排家,欲是人间一富翁。");
        tempMansongMap.put("五两四","此命推来厚且清,诗画满腹看功成。丰衣足食自然稳,正是人间有福人。");
        tempMansongMap.put("五两五","走马扬鞭争名利,少年做事费筹谋。一朝福禄源源至,富贵荣华耀六亲。");
        tempMansongMap.put("五两六","此格推来礼义通,一生福禄用无穷。甜酸苦辣皆尝过,财源滚滚稳且丰。");
        tempMansongMap.put("五两七","福禄丰盈万事全,一生荣耀显双亲。名扬威振人钦敬,处世逍遥似遇春。");
        tempMansongMap.put("五两八","平生福禄自然来,名利双全福禄偕。雁塔题名为贵客,紫袍玉带走金阶。");
        tempMansongMap.put("五两九","细推此格妙且清,必定财高礼又通。甲第之中应有分,扬鞭走马显威荣。");
        tempMansongMap.put("六两","一朝金榜快题名,显祖荣宗立大功。衣食定然原裕足,田园财帛更丰盛。");
        tempMansongMap.put("六两一","不作朝中金榜吝,定为世上一财翁。聪明天赋经书熟,名显高料自是荣。");
        tempMansongMap.put("六两二","此命推来福不穷,读书必定显亲宗。紫衣金带为卿相,富贵荣华皆可同。");
        tempMansongMap.put("六两三","命主为官福禄长,得来富贵定非常。名题雁塔传金榜,定中高科天下扬。");
        tempMansongMap.put("六两四","此格威权不可挡,紫袍金带坐高望。荣华富贵虽能及,积玉堆金满储仓。");
        tempMansongMap.put("六两五","细推此命福不轻,安国安邦极品人。文纷雕梁徽富贵,威声照耀四方闻。");
        tempMansongMap.put("六两六","此格人间一福人,堆金积玉满堂春。从来富贵由天定,正勿垂绅谒圣君。");
        tempMansongMap.put("六两七","此命生来福自宏,田园家业最高隆。平生衣禄丰盈足,一世荣华万事通。");
        tempMansongMap.put("六两八","富贵由大莫苦求,万金家计不须谋。十年不比前番事,祖业根基水上舟。");
        tempMansongMap.put("六两九","君是人间前禄星,一生富贵众人钦。纵然福禄由天定,安享荣华过一生。");
        tempMansongMap.put("七两","此命推来福不轻,不须愁愿苦劳心。一生天定衣与禄,富责荣华主一生。");
        tempMansongMap.put("七两一","此命生来大不同,公侯卿相在其中。一生自有逍遥福,富贵荣华极品隆。");
        mansongMap = Collections.unmodifiableMap(tempMansongMap);

        //初始化 womansongMap
        Map<String, String> tempWomansongMap = new HashMap<>();
        tempWomansongMap.put("二两一","生身此命运不通,乌云盖月黑朦胧,莫向故园载花木,可来幽地种青松。");
        tempWomansongMap.put("二两二","女命孤冷独凄身,此身推来路乞人,操心烦恼难度日,一生痛苦度光阴。");
        tempWomansongMap.put("二两三","女命生来轻薄人,营谋事作难称心,六亲骨肉亦无靠,奔走劳碌困苦门。");
        tempWomansongMap.put("二两四","女命推来福禄无,治家艰难辛苦多,丈夫儿女不亲爱,奔走他乡作游姑。");
        tempWomansongMap.put("二两五","此命一身八字低,家庭艰辛多苦妻,娘家亲友冷如炭,一生勤劳多忧眉。");
        tempWomansongMap.put("二两六","平生依禄但苦求,两次配夫带忧愁,咸酸苦辣他偿过,晚年衣食本无忧。");
        tempWomansongMap.put("二两七","此格做事单独强,难告夫君作主张,心问口来口问心,晚景衣禄宜自生。");
        tempWomansongMap.put("二两八","女命生来八字轻,为善作事也无因,你把别人当亲生,别人对你假殷情。");
        tempWomansongMap.put("二两九","花支艳来硬性身,自奔自力不求人,若问求财方可止,在苦有甜度光阴。");
        tempWomansongMap.put("三两","女命推来比郎强,婚姻大事碍无障,中年走过坎坷地,末年渐经行前强。");
        tempWomansongMap.put("三两一","早年行运在忙头,劳碌奔波苦勤求,赛力劳心把家立,后来晚景名忧愁。");
        tempWomansongMap.put("三两二","时逢运来带吉神,从有凶星转灰尘,真变假来假成真,结拜弟殊当亲生。");
        tempWomansongMap.put("三两三","初限命中有变化,中年可比树落花,勤俭持家难度日,晚年成业享荣华。");
        tempWomansongMap.put("三两四","矮巴勾枣难捞枝,看破红尘最相宜,谋望求财空费力,婚姻三娶两次离。");
        tempWomansongMap.put("三两五","女子走冰怕冰薄,出行交易受残霜,婚姻周郎休此意,官司口舌须相加。");
        tempWomansongMap.put("三两六","忧悉常锁两眉间,家业挂心不等闲,从今以后防口角,任意行移不相关。");
        tempWomansongMap.put("三两七","此命推来费运多,若作摧群受折磨,山路崎岖吊下耳,左插右安心难留。");
        tempWomansongMap.put("三两八","凤鸣岐山四方扬,女命逢此大吉昌,走失夫君音信有,晚年衣禄财盈箱。");
        tempWomansongMap.put("三两九","女命推来运未能,劳碌奔波一场空,好似俊鸟在笼锁,中年未限凄秋风。");
        tempWomansongMap.put("四两","目前月令运不良,千辛万苦受煎熬,女身受得多苦难,晚年福禄比密甜。");
        tempWomansongMap.put("四两一","此命推来一般艰,女子为人很非凡,中年逍遥多自在,晚年更比中年超。");
        tempWomansongMap.put("四两二","杜井破废已多年,今有泉水出来鲜,资生济竭人称美,中运来转喜安然。");
        tempWomansongMap.put("四两三","推车靠涯道路通,女名求财也无穷,婚姻出配无阻碍,疾病口舌离身躬。");
        tempWomansongMap.put("四两四","夜梦金银醒来空,立志谋业运不通,婚姻难成交易散,夫君趟失未见踪。");
        tempWomansongMap.put("四两五","女命终身驳杂多,六亲骨肉不相助,命中男女都难养,劳碌辛苦还奔波。");
        tempWomansongMap.put("四两六","孤舟行水离沙滩,离乡出外早过家,是非口舌皆无碍,婚姻合配紫微房。");
        tempWomansongMap.put("四两七","时来运转喜开颜,多年枯木逢春花,枝叶重生多茂盛,凡人见得都赞夸。");
        tempWomansongMap.put("四两八","一朵鲜花镜中开,看着极好取不来,劝你休把镜花想,此命推来主可癫。");
        tempWomansongMap.put("四两九","二生为人福宏名,心兹随君显门庭,容貌美丽惹人爱,银钱富足万事成。");
        tempWomansongMap.put("五两","马氏太公不相和,好命逢此忧凝多,恩人无义反为仇,是非平地起风波。");
        tempWomansongMap.put("五两一","肥羊一群入出场,防虎逢之把口张,适口充饥心欢喜,女命八字大吉昌。");
        tempWomansongMap.put("五两二","顺风行舟扯起帆,上天又助一顺风,不用费力逍遥去,任意顺行大享通。");
        tempWomansongMap.put("五两三","此命相貌眉且清,文武双全功名成,一生衣禄皆无缺,可算世上积福人");
        tempWomansongMap.put("五两四","运开满腹好文章,谋事求财大吉祥,出行交易多得稳,到处享通姓名扬。");
        tempWomansongMap.put("五两五","发政旅仁志量高,女命求财任他乡,交舍婚姻多有意,无君出外有音耗。");
        tempWomansongMap.put("五两六","明珠辉吐离埃来,女有口有清散开,走失郎君当两归,交易有成永无灾。");
        tempWomansongMap.put("五两七","游鱼戏水被网惊,踊身变化入龙门,三根杨柳垂金钱,万朵桃花显价能。");
        tempWomansongMap.put("五两八","此命推来转悠悠,时运未来莫强求,幸得今日重反点,自有好运在后头。");
        tempWomansongMap.put("五两九","雨雪载途活泥泞,交易不安难出生,疾病还拉婚姻慢,谋望求财事难寻。");
        tempWomansongMap.put("六两","女命八字喜气和,谋事求财吉庆多,口舌渐消疾病少,夫君走别归老窠。");
        tempWomansongMap.put("六两一","绿木求鱼事多难,虽不得鱼无害反,若是行险弄巧地,事不遂心枉安凡。");
        tempWomansongMap.put("六两二","指日高开气象新,走失待人有信音,好命遇事遂心好,伺病口舌皆除根。");
        tempWomansongMap.put("六两三","五官脱运难抬头,妇命须当把财求,交易少行有人助,一生衣禄不须愁。");
        tempWomansongMap.put("六两四","俊鸟曾得出胧中,脱离为难显威风,一朝得意福力至,东南西北任意通。");
        tempWomansongMap.put("六两五","女命推来福非轻,兹善为事受人敬,天降文王开基业,八百年来富贵门。");
        tempWomansongMap.put("六两六","时来运转闺阁楼,贤德淑女君子求,击鼓乐之大吉庆,女命逢此喜悠悠。");
        tempWomansongMap.put("六两七","乱丝无头定有头,碰得亲事暂折磨,交易出行无好处,谋事求财心不遂。");
        tempWomansongMap.put("六两八","水底明月不可捞,女命早限运未高,交易出行难获利,未运终得渐见好。");
        tempWomansongMap.put("六两九","太公封祖不非凡,女子求财稳如山,交易合伙多吉庆,疾病口角遗天涯。");
        tempWomansongMap.put("七两","本命推断喜气新,恰遇郎君金遂心,坤身来交正当运,富贵衣禄乐平生。");
        tempWomansongMap.put("七两一","此命推来宏运交,不须再愁苦劳难,一生身有衣禄福,安享荣华胜班超。");

        womansongMap = Collections.unmodifiableMap(tempWomansongMap);
    }

    public static String convertToTraditionalChineseUnits(double number) {
        int liang = (int) number;
        BigDecimal decimal = new BigDecimal(String.valueOf(number));
        BigDecimal qianDecimal = decimal.subtract(new BigDecimal(liang)).multiply(BigDecimal.TEN);
        int qian = qianDecimal.intValue();

        StringBuilder sb = new StringBuilder();
        if (liang > 0) {
            sb.append(chineseNumber(liang)).append("两");
        }
        if (qian > 0) {
            sb.append(chineseNumber(qian)).append("钱");
        }

        return sb.toString();
    }

    public static String chineseNumber(int number) {
        String[] chineseNumbers = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] chineseUnits = {"", "十", "百", "千"};

        StringBuilder sb = new StringBuilder();
        String str = String.valueOf(number);

        for (int i = 0; i < str.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(str.charAt(i)));
            if (digit != 0) {
                sb.append(chineseNumbers[digit]).append(chineseUnits[str.length() - i - 1]);
            } else {
                // 处理连续多个零的情况，只保留一个零
                if (i < str.length() - 1 && str.charAt(i + 1) != '0') {
                    sb.append(chineseNumbers[digit]);
                }
            }
        }

        return sb.toString();
    }
}
