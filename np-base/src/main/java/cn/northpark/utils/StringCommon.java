package cn.northpark.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作字符串的常见方法等等
 *
 */
@Slf4j
public class StringCommon {

	/**
	 * 根据字符串拼接IN 的字符串
	 * 
	 * @param type1
	 * @return
	 */
	public static String getSplitInStr(String type1) {
		String[] split = type1.split(",");
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < split.length; i++) {
			if (i < split.length - 1) {

				sb.append("'").append(split[i]).append("'").append(",");
			} else {
				sb.append("'").append(split[i]).append("'");
			}
		}
		sb.append(")");

		return sb.toString();
	}
	
	
	
	/**
	    *     根据List<1,2,3>拼接  '1','2','3'
	 * 
	 * @param type1
	 * @return
	 */
	public static String getSplitByList(List<String> strs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.size(); i++) {
			if (i < strs.size() - 1) {

				sb.append("'").append(strs.get(i)).append("'").append(",");
			} else {
				sb.append("'").append(strs.get(i)).append("'");
			}
		}

		return sb.toString();
	}

	/**
	 * 根据页面配置的指标参数拼接指标参数值 1^2^3 ....
	 * 
	 * @param param_map
	 * @return
	 */
	public static String getZBparamVals(LinkedHashMap<String, Object> param_map) {
		StringBuilder sb = new StringBuilder();
		param_map.forEach((k, v) -> {
			sb.append(v).append("^");
		});
		if (sb.toString().endsWith("^")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		log.info(" 根据页面配置的指标参数拼接指标参数值----->" + sb.toString());
		return sb.toString();
	}
	
	
	
	/**
	 * 描述： //这个函数的作用就是使用空格分割字符串，以便后面使用分割函数使得将字符串分割成数组
	 *
	 * @param s
	 * @return
	 */
	public static String replaceBlanks(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '&' || s.charAt(i) == '|'
					|| s.charAt(i) == '!')
				result += " " + s.charAt(i) + " ";
			else
				result += s.charAt(i);
		}
		return result;
	}

	/**
	 * 描述：用队列来接收需要处理的对象
	 *
	 * @param managers
	 * @return
	 */
	public static LinkedList<String> getKuoHaoList(String managers) {
		LinkedList<String> ls = new LinkedList<String>();
		Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
		Matcher matcher = pattern.matcher(managers);
		while (matcher.find())
			ls.add(matcher.group());
		return ls;
	}
	
	
	/**
	 * 根据 value值按照\,\分割后拼接单引号和括号 返回拼接后的字符串
	 * 
	 * @param vc_dim_value
	 */
	public static String getSplitStr(String vc_dim_value) {
		StringBuilder sbsplit = new StringBuilder();
		sbsplit.append("(");
		String[] split = vc_dim_value.split(",");
		for (int i = 0; i < split.length; i++) {
			if (i != split.length - 1) {
				sbsplit.append("'").append(split[i]).append("'").append(",");
			} else {
				sbsplit.append("'").append(split[i]).append("'");
			}

		}

		sbsplit.append(")");
		return sbsplit.toString();
	}

	/**
	 * 根据 value值按照\,\分割后拼接括号 返回拼接后的字符串 :表达式无引号
	 * 
	 * @param vc_dim_value
	 */
	public static String getSplitStrExpress(String vc_dim_value) {
		StringBuilder sbsplit = new StringBuilder();
		sbsplit.append("(");
		String[] split = vc_dim_value.split(",");
		for (int i = 0; i < split.length; i++) {
			if (i != split.length - 1) {
				sbsplit.append(split[i]).append(",");
			} else {
				sbsplit.append(split[i]);
			}

		}

		sbsplit.append(")");
		return sbsplit.toString();
	}
	
	/**
	 * 判断字段是否为空,为空返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String checkColumnNotNull(Object str) {
		if (str != null) {
			return "'" + str + "'";
		} else {
			return null;
		}
	}
	
	
	/**
	 * 写入文件
	 * @author jeyy
	 * @param response 
	 * @param data 写入的内容
	 * @param fileName 交互？要保存的文件名
	 * @throws IOException
	 */
	public static void writeFile(HttpServletResponse response, String data, String fileName) throws IOException {
		
        String enfileName = "";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        try {
            enfileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;filename="+enfileName);
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			
			IOUtils.write(data, outputStream);
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			
			IOUtils.closeQuietly(outputStream);
		}
	}


	/**
	 * 截取字符串固定长度
	 * @param str
	 * @param len
	 * @return
	 */
	public static String getLenStr(String str, int len) {
		String returnStr = "";
		int str_length = 0;
		int str_len = 0;
		String str_cut = new String();
		str_len = str.length();
		for (int i = 0; i < str_len; i++) {
			char a = str.charAt(i);
			str_length++;
			if (escape(a + "").length() > 4) {
				//中文字符的长度经编码之后大于4
				str_length++;
			}
			str_cut = str_cut.concat(a + "");
			if (str_length >= len) {
				str_cut = str_cut.concat("...");
				returnStr = str_cut;
				break;
			}
		}
		//如果给定字符串小于指定长度，则返回源字符串；
		if (str_length < len) {
			returnStr = str;
		}
		return returnStr;
	}

	private static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();

	}


		public static void main(String[] args) {
		LinkedHashMap<String, Object> param_map = new LinkedHashMap<String, Object>();
		param_map.put("DURATION_SRC", "1");
		param_map.put("DURATION_WEIGHT", "2");
		getZBparamVals(param_map);

	}

}
