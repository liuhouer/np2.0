package cn.northpark.YI.PredictLuckNumber;

import cn.northpark.utils.EmailUtils;
import cn.northpark.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class PredictLuckNumberApp {

	public static void main(String[] args) throws Exception {
		// 获取当前日期时间
		// 获取当前日期
//		LocalDate currentDate = LocalDate.now();
//
//		// 计算前面10天的日期
//		LocalDate startDate = currentDate.minusDays(10);
//
//		// 定义日期格式化器
//		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//		// 格式化日期为 "yyyy-MM-dd" 格式
//		for (int i = 0; i < 10; i++) {
//			String formattedDate = startDate.plusDays(i).format(dateFormatter);
//			System.out.println(formattedDate);
//
//			predictNumbers(formattedDate);
//		}

		predictNumbers(TimeUtils.nowdate());

	}

	private static void predictNumbers(String prefixDay) throws Exception {
		// 创建格式化类，设定格式为 yyyy-MM-dd HH:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 解析日期时间为字符串
		String currentDateTimeString =  prefixDay +" 21:15:00";

		// 输出当前日期时间
		System.out.println("当前日期时间: " + currentDateTimeString);

		// 创建Bazi对象，传入当前日期时间参数
		Date currentDate = sdf.parse(currentDateTimeString);
		Bazi test = new Bazi(currentDate);

		// 输出时支八字
		String timeTG = test.getTimeTG();
		System.out.println(timeTG);

		// 获取幸运的标题
		TwelveZodiac twelveZodiac = new TwelveZodiac();
		List<String> luckestColByRowName = twelveZodiac.getLuckestColByRowName(timeTG);

		// 获取河图洛书编号
		String heLuoNo = HeTuLuoShu.getHeLuoNo(luckestColByRowName);
		System.out.println("今日幸运数字: " + heLuoNo);

		StringBuilder sb = new StringBuilder();
		sb.append("<br>");
		sb.append("当前日期时间: " + currentDateTimeString);
		sb.append("<br>");
		sb.append("今日幸运数字: " + heLuoNo);
		sb.append("<br>");
		sb.append("=======================================");
		sb.append("<br>");
		//星座运势
		sb.append(XZYS.getXZYS());
		String sendEMAIL = EmailUtils.getInstance().sendEMAIL("654714226@qq.com", "今日运势预测", sb.toString());

		System.err.println("sendEMAIL flag====="+sendEMAIL);
	}

}
