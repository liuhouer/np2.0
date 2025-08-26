package cn.northpark.utils;

import cn.northpark.utils.encrypt.NorthParkCryptUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * @author jeyy
 *
 */
@Slf4j
public class EmailUtils {

    private static final String EMAIL_PASS = EnvCfgUtil.getValByCfgName("EMAIL_PASS");

	private EmailUtils() {

	}


	private volatile static EmailUtils instance = null;

	/**
	 *  双重同步锁模式【volatile出坑】
	 	在对象声明时使用volatile关键字修饰，阻止CPU的指令重排。
	 */
	public static EmailUtils getInstance() {
		if(instance == null) {
			synchronized (EmailUtils.class) {
				if(instance == null) {
					instance = new EmailUtils();
				}
			}
		}
		return instance;
	}




    /**
     * 找回密码邮件认证
     *
     * @param toEmail
     * @param usrId
     * @param authCode
     */
    public void changePwd(String toEmail, String usrId, String authCode) {
        String subject = "找回NorthPark密码";
        String content = "您的密码重置验证码是：" + authCode + "，请点击链接重置密码：https://northpark.cn/cm/reset?userid=" + usrId + "&auth_code=" + authCode;
        sendEMAIL(toEmail, subject, content);
    }

    /**
     * 资源失效反馈邮件提醒
     *
     * @param msg
     */
    public void resFeedBack(String msg) {
        String subject = "资源失效反馈";
        String content = "收到资源失效反馈：" + msg + " - " + TimeUtils.nowTime();
        sendEMAIL("zhangyang226@gmail.com", subject, content);
    }

    /**
     * 发送自定义邮件
     * @param emailHost
     * @param title
     * @param msg
     */
    public String sendEMAIL(String emailHost, String title, String msg) {
        try {
            String host = "smtp.163.com";
            String myEmail = "qhdsoftware@163.com";
            String myPassword = NorthParkCryptUtils.diyDecrypt(EMAIL_PASS);

            // 使用HTML标签包裹邮件内容
            msg = "<html><body>" + msg + "</body></html>";

            HtmlEmail email = new HtmlEmail();
            email.setHostName(host);
            email.setAuthenticator(new DefaultAuthenticator(myEmail, myPassword));
            email.setSSLOnConnect(true);
            email.setSmtpPort(465);
            email.setFrom(myEmail, "northpark官方");
            email.setSubject(title);
            email.addTo(emailHost, "用户");
            email.setCharset("utf-8");
            email.setHtmlMsg(msg);
            
            String result = email.send();
            log.info("邮件发送成功");
            return result;

        } catch (Exception e) {
            log.error("邮件发送失败", e);
            return "send error";
        }
    }

    /**
     * 正则验证邮箱格式
     *
     * @param email 邮箱
     * @return Boolean
     */
    public static boolean isEmail(String email) {
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return email.matches(regex);
    }


}
