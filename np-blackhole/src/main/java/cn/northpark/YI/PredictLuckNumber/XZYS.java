package cn.northpark.YI.PredictLuckNumber;

import cn.northpark.utils.EnvCfgUtil;
import com.google.gson.Gson;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
**/
public class XZYS {
    public static void main(String[] args) {
        getXZYS();
    }

    public static String getXZYS() {
        StringBuilder sb = new StringBuilder();
        String key = EnvCfgUtil.getValByCfgName("XZYS_KEY");
        String consName = "摩羯座";

        // 发送请求并获取响应
        String todayUrl = "http://web.juhe.cn/constellation/getAll?key=" + key + "&consName=" + consName + "&type=today";
        String weekUrl = "http://web.juhe.cn/constellation/getAll?key=" + key + "&consName=" + consName + "&type=week";

        String todayResponse = sendRequest(todayUrl);
        String weekResponse = sendRequest(weekUrl);

        // 解析返回的JSON数据并组装到实体类中
        Gson gson = new Gson();
        TodayHoroscope todayHoroscope = gson.fromJson(todayResponse, TodayHoroscope.class);
        WeekHoroscope weekHoroscope = gson.fromJson(weekResponse, WeekHoroscope.class);

        // 打印实体类内容
        System.out.println("Today Horoscope:");
        System.out.println(todayHoroscope.toString());

        sb.append(todayHoroscope.toString());

        sb.append("<br>");

        System.out.println("Week Horoscope:");
        System.out.println(weekHoroscope.toString());
        sb.append(weekHoroscope.toString());

        return sb.toString();

    }

    private static String sendRequest(String url) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

@Data
class TodayHoroscope {
    private String name;
    private String datetime;
    private int date;
    private String all;
    private String color;
    private String health;
    private String love;
    private String money;
    private int number;
    private String QFriend;
    private String summary;
    private String work;
    private int error_code;

    // Getters and setters


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double overallStars = calculateStars(all);
        double healthStars = calculateStars(health);
        double loveStars = calculateStars(love);
        double moneyStars = calculateStars(money);
        double workStars = calculateStars(work);

        sb.append("摩羯座今日运势:");
        sb.append("<br>");
        sb.append("[日期]" + datetime );
        sb.append("<br>");
        sb.append("[综合指数]" + all + "（" + getStarsString(overallStars) + "）");
        sb.append("<br>");
        sb.append("[健康指数]" + health + "（" + getStarsString(healthStars) + "）");
        sb.append("<br>");
        sb.append("[爱情指数]" + love + "（" + getStarsString(loveStars) + "）");
        sb.append("<br>");
        sb.append("[财运指数]" + money + "（" + getStarsString(moneyStars) + "）");
        sb.append("<br>");
        sb.append("[工作指数]" + work + "（" + getStarsString(workStars) + "）");
        sb.append("<br>");
        sb.append("[幸运色]" + color );
        sb.append("<br>");
        sb.append("[幸运数字]" + number );
        sb.append("<br>");
        sb.append("[速配星座]" + QFriend );
        sb.append("<br>");
        sb.append("[今日概述]" + summary );
        sb.append("<br>");
        return sb.toString();
    }

    private double calculateStars(String index) {
        int value = Integer.parseInt(index);
        int starsN = value / 20;  // 每20为一颗星
        double stars = starsN;  // 每20为一颗星
        int remainder = value % 20;
        if (remainder >= 10) {
            stars = starsN + 0.5;  // 10为半颗星
        }
        return stars;
    }

    private String getStarsString(double stars) {
        int fullStars = (int) stars;
        Double halfStarsD =  ((stars - fullStars)/0.5);
        int halfStars = halfStarsD.intValue();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fullStars; i++) {
            sb.append("★");
        }
        for (int i = 0; i < halfStars; i++) {
            sb.append("✬");
        }
        for (int i = fullStars+halfStars;i<5;i++) {
            sb.append("☆");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        TodayHoroscope n1 = new TodayHoroscope();
        for (int i = 0; i <= 100; i=i+10) {
            double overallStars = n1.calculateStars(String.valueOf(i));
            System.err.println(overallStars);
            System.err.println(n1.getStarsString(overallStars));
        }

    }
}

@Data
class WeekHoroscope {
    private String name;
    private String date;
    private int weekth;
    private String health;
    private String job;
    private String love;
    private String money;
    private String work;
    private String resultcode;
    private int error_code;

    // Getters and setters

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("摩羯座本周运势:");
        sb.append("<br>");
        sb.append("[日期]" + date );
        sb.append("<br>");
        sb.append("[健康情况]" + health );
        sb.append("<br>");
        sb.append("[爱情情况]" + love );
        sb.append("<br>");
        sb.append("[财运情况]" + money );
        sb.append("<br>");
        sb.append("[事业情况]" + work );
        sb.append("<br>");
        return sb.toString();
    }


}


