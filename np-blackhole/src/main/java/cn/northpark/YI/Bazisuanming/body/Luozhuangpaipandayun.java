package cn.northpark.YI.Bazisuanming.body;

/**
 * Created by Jeey  .
 */
import java.text.ParseException;
import java.util.Calendar;

/**
 *排大运
 * @author luozhuang 大师♂罗莊
 */
public class Luozhuangpaipandayun {

    LuozhuangshenshaHehun myLuozhuangshenshaHehun = new LuozhuangshenshaHehun();
    Luozhuanglvhehun myLuozhuanglvhehun = new Luozhuanglvhehun();

    /**
     *
     * @param man 生日 yyyy-MM-dd HH
     * @return 返回乾造
     * @throws ParseException
     */
    public String paipan(String man, Sex isman) throws ParseException {

        Calendar mancal;

        try {
            mancal = myLuozhuangshenshaHehun.getCalendarfromString(man, "yyyy-MM-dd HH");
            //原来的private 方法改了下
        } catch (ParseException ex) {
            return "输入不正确" + ex.getMessage();
        }

        return paipan(mancal, isman);

    }


    /**找数组中月柱起始位置
     *
     * @param jiazhi
     * @param yuezhu
     * @return
     */
    public int getyuezhuStart(String[] jiazhi, String yuezhu) {

        int start = -1;
        for (int i = 0; i < jiazhi.length; i++) {
            if (yuezhu.equals(jiazhi[i])) {
                start = i;
                break;
            }

        }

        return start;
    }
//顺行排大运

    private String[] shundayun(String yuezhu) {

        String[] DayunStringArray = new String[8];//取八个

        String[] jiazhi = myLuozhuanglvhehun.jiazhi;
        int start = getyuezhuStart(jiazhi, yuezhu);
        if (start == -1) {
            return null;
        }
        else
        {
            start++;
        }
        for (int i = 0; i < 8; i++) {
            DayunStringArray[i] = jiazhi[(start + i) % jiazhi.length];
        }

        return DayunStringArray;

    }
//逆行排大运

    private String[] nidayun(String yuezhu) {
        String[] DayunStringArray = new String[8];//取八个

        String[] jiazhi = myLuozhuanglvhehun.jiazhi;
        int start = getyuezhuStart(jiazhi, yuezhu);
        if (start == -1) {
            return null;
        }
        else
        {
            start--;
        }
        for (int i = 0; i < 8; i++) {
            DayunStringArray[i] = jiazhi[(start - i) % jiazhi.length];
        }

        return DayunStringArray;

    }
    //大运用月柱排

    public String[] Dayun(String nianzhu,String yuezhu,Sex isman) {
        String[] DayunStringArray = null;
        if (yuezhu == null || yuezhu.length() != 2) {
            return null;
        }


        //甲、丙、戊、庚、壬之年为阳，乙、丁、己、辛、癸之年为阴
        //阴年生男子（或阳年生女子），大运逆行否则顺行
        if (nianzhu.startsWith("甲") || nianzhu.startsWith("丙") || nianzhu.startsWith("戊") || nianzhu.startsWith("庚") || nianzhu.startsWith("庚") || nianzhu.startsWith("壬")) {
            if (isman == Sex.MAN) {//顺行
                DayunStringArray= shundayun(yuezhu);
            } else {
                DayunStringArray=nidayun(yuezhu);
            }

        } else {
            if (isman == Sex.MAN) {
                DayunStringArray= nidayun(yuezhu);
            } else {
                DayunStringArray=shundayun(yuezhu);
            }

        }
        return DayunStringArray;
    }

    private String paipan(Calendar cal, Sex isman) throws ParseException {

        BaZi lunar = new BaZi(cal);
        System.out.println("此人农历的日期【" + lunar.toString() + "】");
//        /**
//         * 很多地方都是按照23：00-1：00为子时这是不对的。
//         * 子时24.00－2.00,丑时2.00－4.00,寅时4.00－6.00,卯时6.00－8.00,
//         * 辰时8.00－10.00,巳时10.00－12.00,午时12.00－14.00,未时14.00－16.00
//         * 申时16.00－18.00,酉时18.00－20.00,戌时20.00－22.00,亥时22.00－24.00
//         *
//         */
//        int time = cal.get(Calendar.HOUR_OF_DAY) / 2;
        /**
         * 子时23：00-1：00,丑时1.00－3.00,寅时3.00－5.00,卯时5.00－7.00,
         * 辰时7.00－9.00,巳时9.00－11.00,午时11.00－13.00,未时13.00－15.00
         * 申时15.00－17.00,酉时17.00－19.00,戌时19.00－21.00,亥时21.00－23.00
         *
         */
        int time = (cal.get(Calendar.HOUR_OF_DAY)+25)%24/ 2;
        System.out.println("此人八字【" + lunar.getYearGanZhi(time) + "】");
        //获取生肖
        System.out.println("此人的农历生肖【" + lunar.animalsYear() + "】");



        String GanZhi = lunar.getYearGanZhi(time);//取八字
        String[] tempchar = GanZhi.split(",");
        //我修改原来的，用,分割
        String ganziyear = tempchar[0];//年柱
        String ganzimonth = tempchar[1];//月柱
        String ganziday = tempchar[2];//日柱
        String ganzitime = tempchar[3];//时柱

        //五行纳音

        String soundyear = myLuozhuangshenshaHehun.getnumsix(ganziyear);
        String soundmonth = myLuozhuangshenshaHehun.getnumsix(ganzimonth);
        String soundday = myLuozhuangshenshaHehun.getnumsix(ganziday);
        String soundtime = myLuozhuangshenshaHehun.getnumsix(ganzitime);

        String[] DayunArray = Dayun(ganziyear,ganzimonth, isman);
        pringst(DayunArray);
        return null;
    }

    public static void pringst(String[] res) {
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
            System.out.print("   ");
        }
        System.out.println("");
    }

    /**
     * @author Jeey
     * 根据大运排出的年份 和出生年份计算偏移量
     * @param ganziyear
     * @param dayunArray
     * @return
     */
    public static int calDayunOffset(String ganziyear,String[] dayunArray ) {
        String[] jiazhiArray = BaZi.jiazhi;
        int ganziyearIndex = -1; // 初始化ganziyear索引位置为-1
        int jiIndex = -1; // 初始化己的索引位置为-1

        for (int i = 0; i < jiazhiArray.length; i++) {
            if (jiazhiArray[i].equals(ganziyear)) {
                ganziyearIndex = i; // 找到ganziyear对象，记录索引位置
            }
        }
        for (int i = ganziyearIndex; i < jiazhiArray.length; i++) {
            if (jiazhiArray[i].startsWith(dayunArray[0].substring(0,1))) {
                jiIndex = i; // 找到第一个"己"，记录索引位置
                break; // 找到后跳出循环
            }
        }

        int offset = jiIndex - ganziyearIndex; // 计算差值

        System.out.println("ganziyear索引位置: " + ganziyearIndex);
        System.out.println("己的索引位置: " + jiIndex);
        System.out.println("差值: " + offset);
        return  offset ;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
//        String[] test = new String[2];
//        test[0]="a";
//        test[1]="b";
//        System.out.print(String.format(":%s,:%s",test));

        Luozhuangpaipandayun my=new Luozhuangpaipandayun();
        my.paipan("1991-12-31 13", Sex.MAN);

    }
}