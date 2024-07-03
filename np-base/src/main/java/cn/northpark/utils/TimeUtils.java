package cn.northpark.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 *
 * @author bruce
 * @since 2014-05-23
 */
@Slf4j
public class TimeUtils {
    private static final long ONE_MINUTE = TimeUnit.MINUTES.toSeconds(1);
    private static final long ONE_HOUR = TimeUnit.HOURS.toSeconds(1);
    private static final long ONE_DAY = TimeUnit.DAYS.toSeconds(1);
    private static final long ONE_MONTH = TimeUnit.DAYS.toSeconds(30);
    private static final long ONE_YEAR = TimeUnit.DAYS.toSeconds(365);

    private static final String[] TIME_UNITS = {"秒前", "分钟前", "小时前", "天前", "月前", "年前"};

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat ENGLISH_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static String formatToNear(String str) {
        try {
            SimpleDateFormat format = str.length() > 10 ? DATE_FORMAT : SHORT_DATE_FORMAT;
            Date date = format.parse(str);
            return format(date);
        } catch (ParseException e) {
            log.error("TimeUtils parsing error", e);
            return str;
        }
    }

    public static String format(Date date) {
        long ago = (System.currentTimeMillis() - date.getTime()) / 1000;

        if (ago <= ONE_MINUTE) return ago + TIME_UNITS[0];
        if (ago <= ONE_HOUR) return ago / ONE_MINUTE + TIME_UNITS[1];
        if (ago <= ONE_DAY) return ago / ONE_HOUR + TIME_UNITS[2];
        if (ago <= ONE_DAY * 2) return "昨天";
        if (ago <= ONE_DAY * 3) return "前天";
        if (ago <= ONE_MONTH) return ago / ONE_DAY + TIME_UNITS[3];
        if (ago <= ONE_YEAR) return ago / ONE_MONTH + TIME_UNITS[4];
        return ago / ONE_YEAR + TIME_UNITS[5];
    }

    public static String getNowTime() {
        return DATE_FORMAT.format(new Date());
    }

    public static String getEnglishDate() {
        return ENGLISH_DATE_FORMAT.format(new Date());
    }

    public static String parse2EnglishDate(String date) {
        try {
            Date parsedDate = SHORT_DATE_FORMAT.parse(date);
            return ENGLISH_DATE_FORMAT.format(parsedDate);
        } catch (ParseException e) {
            log.error("Error parsing date", e);
            return date;
        }
    }


    /**
     * 字符串(格式：yyyy-MM-dd hh:mm:ss) --> 毫秒(说明：1970年1月1日0时起到当前字符串时间的毫秒)
     *
     * @return 毫秒数(1970年1月1日0时起到当前字符串时间的毫秒)
     */
    public static long stringToMillis(String source) {
        try {
            LocalDateTime dateTime = source.length() > 10
                    ? LocalDateTime.parse(source, DATE_TIME_FORMATTER)
                    : LocalDate.parse(source, DATE_FORMATTER).atStartOfDay();
            return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            log.error("Error parsing date: " + source, e);
            return System.currentTimeMillis();
        }
    }

    /**
     * @author bruce 取得当前时间 格式yyyy-MM-dd hh:mm:ss
     */
    public static String nowTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    /**
     * @author bruce 取得时钟表时间 hh:mm:ss
     */
    public static String nowClock() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    /**
     * @author bruce 取得当前日期
     */
    public static String nowDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * @author bruce 取得N个月以后的日期
     */
    public static String N_MonthDate(int n) {
        return LocalDate.now().plusMonths(n).format(DATE_FORMATTER);
    }

    /**
     * @author bruce 取得N年以后的日期
     */
    public static String N_YearDate(int n) {
        return LocalDate.now().plusYears(n).format(DATE_FORMATTER);
    }

    /**
     * @author bruce 取得N年以后的日期
     */
    public static String N_YearTime(int n) {
        return LocalDateTime.now().plusYears(n).format(DATE_TIME_FORMATTER);
    }

    /**
     * @author bruce
     * @function 取得标准时间 2014-05-23从格式yyyy-MM-dd hh:mm:ss
     */
    public static String getHalfDate(String timeStr) {
        return timeStr.length() > 10 ? timeStr.substring(0, 10) : timeStr;
    }

    /**
     * @author bruce
     * @function 取得标准时间 2014从格式yyyy-MM-dd hh:mm:ss
     */
    public static String getYear(String timeStr) {
        return timeStr.length() >= 4 ? timeStr.substring(0, 4) : timeStr;
    }

    /**
     * 获取月份
     *
     * @param time 标准时间字符串（yyyy-MM-dd hh:mm:ss）
     * @return 月份
     */
    public static String getMonth(String time) {
        if (time == null || !time.contains("-")) {
            return null;
        }
        String[] parts = time.split("-");
        return parts.length > 1 ? parts[1] : null;
    }

    /**
     * @author bruce
     * @function 取得标准时间 2014从格式yyyy-MM-dd hh:mm:ss
     */
    public static String getDay(String timeStr) {
        if (timeStr == null || !timeStr.contains("-")) {
            return timeStr;
        }
        String[] parts = timeStr.split("-");
        return parts.length > 2 ? parts[2].split(" ")[0] : timeStr;
    }


    /**
     * @param nowtime  当前时间
     * @param overtime 过期时间
     * @return true未过期；false过期
     * @throws ParseException
     * @author bruce
     * @function 计算是否过期, true未过期；false过期
     */
    public static boolean isInvalid(String nowtime, String overtime) throws ParseException {
        LocalDateTime now = LocalDateTime.parse(nowtime, DATE_TIME_FORMATTER);
        LocalDateTime over = LocalDateTime.parse(overtime, DATE_TIME_FORMATTER);
        return now.isBefore(over) || now.isEqual(over);
    }

    /**
     * @param specifiedDay 指定日期
     * @return 前一天的时间
     * @desc 取得前一天的时间
     */
    public static String getDayBefore(String specifiedDay) {
        LocalDateTime dateTime = LocalDateTime.parse(specifiedDay, DATE_TIME_FORMATTER);
        return dateTime.minusDays(1).format(DATE_TIME_FORMATTER);
    }

    /**
     * @param specifiedDay 指定日期
     * @return 后一天的时间
     * @desc 取得后一天的时间
     */
    public static String getDayAfter(String specifiedDay) {
        LocalDateTime dateTime = LocalDateTime.parse(specifiedDay, DATE_TIME_FORMATTER);
        return dateTime.plusDays(1).format(DATE_TIME_FORMATTER);
    }


    /**
     * @param specifiedDay 指定日期
     * @param N            偏移量
     * @param type         类型[D:DAY | M:MONTH |Y:YEAR |H:HOUR | MIN:minute | S:second]
     * @return 偏移后的时间
     * @author bruce
     * @desc 取得某时间  前后N 天/月/年的时间,N=正负数，type=类型[D:DAY | M:MONTH |Y:YEAR |H:HOUR | MIN:minute | S:second]
     */
    public static String getDateAfterOrBeforeN(String specifiedDay, int N, String type) {
        LocalDateTime dateTime = LocalDateTime.parse(specifiedDay, DATE_TIME_FORMATTER);
        switch (type) {
            case "D":
                dateTime = dateTime.plusDays(N);
                break;
            case "M":
                dateTime = dateTime.plusMonths(N);
                break;
            case "Y":
                dateTime = dateTime.plusYears(N);
                break;
            case "H":
                dateTime = dateTime.plusHours(N);
                break;
            case "MIN":
                dateTime = dateTime.plusMinutes(N);
                break;
            case "S":
                dateTime = dateTime.plusSeconds(N);
                break;
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * @param specifiedDay
     * @return
     * @author bruce
     * @desc 取得前后N天的时间, N=正负数
     */
    public static String getDayAfterOrBeforeN(String specifiedDay, int N) {

        try {
            LocalDateTime dateTime = LocalDateTime.parse(specifiedDay, DATE_TIME_FORMATTER);
            LocalDateTime resultDateTime = dateTime.plusDays(N);
            return resultDateTime.format(DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            log.error("TimeUtils------->Error parsing date: " + specifiedDay, e);
            return null;
        }
    }

    /**
     * @param specifiedDay
     * @return
     * @author bruce
     * @desc 取得前后N分钟后的时间, N=正负数
     */
    public static String getMinuteAfterOrBeforeN(String specifiedDay, int N) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(specifiedDay, DATE_TIME_FORMATTER);
            LocalDateTime resultDateTime = dateTime.plusMinutes(N);
            return resultDateTime.format(DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            log.error("TimeUtils------->Error parsing date: " + specifiedDay, e);
            return null;
        }
    }

    /**
     * @return
     * @author bruce
     * @desc 取得当前的年月比如： 1407
     */
    public static String getYearMonth() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(new Date().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        return dateFormat.format(c.getTime());
    }


    /**
     * @return
     * @author bruce
     * @desc 把long转化成String
     * 将长时间格式字符串转换为字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String longToString(Long time) {
        Date date = new Date(time);
        return DATE_FORMAT.format(date);
    }


    /**
     * @return
     * @desc 随机年07-17
     */
    public static String getRandomYear() {

        String a = "";
        Random random = new Random();
        int max = 17;
        int min = 14;

        int s = random.nextInt(max) % (max - min + 1) + min;


        if (String.valueOf(s).length() == 1) {
            a = "20" + 0 + "" + s;
        } else {
            a = "20" + s + "";
        }

        return a;

    }

    /**
     * @return
     * @desc 随机月01-12
     */
    public static String getRandomMonth() {

        String a = "";
        Random random = new Random();
        int max = 12;
        int min = 01;

        int s = random.nextInt(max) % (max - min + 1) + min;
        if (String.valueOf(s).length() == 1) {
            a = 0 + "" + s;
        } else {
            a = s + "";
        }

        return a;

    }

    /**
     * @return
     * @desc 随机日1-31
     */
    public static String getRandomDay() {

        String a = "";
        Random random = new Random();
        int max = 31;
        int min = 01;

        int s = random.nextInt(max) % (max - min + 1) + min;
        if (String.valueOf(s).length() == 1) {
            a = 0 + "" + s;
        } else {
            a = s + "";
        }
        return a;

    }


    /**
     * @return
     * @desc 随机日期从2007-至今
     */
    public static String getRandomDate() {

        return getRandomYear() + "-" + getRandomMonth() + "-" + getRandomDay();

    }



    /**
     * 根据 几年前|几秒前|几分钱|几天前|几个月前 分析出具体时间啊
     *
     * @param time
     * @return
     */
    public static String getTimeByFanyi(String time) {
        String[] unitCodes = { "S", "MIN", "H", "D", "M" ,"Y"};

        for (int i = 0; i < TIME_UNITS.length; i++) {
            if (time.contains(TIME_UNITS[i])) {
                String delta = time.replace(TIME_UNITS[i], "");
                System.out.println(delta);
                return TimeUtils.getDateAfterOrBeforeN(TimeUtils.nowTime(), Integer.parseInt("-" + delta), unitCodes[i]);
            }
        }

        if (time.contains("昨天")) {
            System.out.println("1");
            return TimeUtils.getDateAfterOrBeforeN(TimeUtils.nowTime(), -1, "D");
        }

        return "";
    }

    /**
     * 把2020.01.01 转为 2020-01-01
     * @param date
     * @return
     */
    public static String pointToSimle(String date) throws ParseException{
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        return localDate.format(DATE_FORMATTER);
    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author bruce
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        return !nowTime.before(startTime) && !nowTime.after(endTime);
    }

    /**
     * 判断当前时间是否在工作时间点[startTime, endTime]区间，注意时间格式要一致
     *
     * @return
     * @author bruce
     */
    public static boolean isWorkClockTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date nowTime = sdf.parse(nowClock());
            Date startTime = sdf.parse("09:00:00");
            Date endTime = sdf.parse("19:00:00");

            boolean flag = isEffectiveDate(nowTime, startTime, endTime);
            System.out.println("now clock--" + nowClock());
            System.out.println(flag);
            return flag;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
