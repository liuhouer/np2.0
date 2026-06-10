package cn.northpark.np5.utils;

import cn.northpark.np5.utils.encrypt.Base64Utils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailUtils {

    private EmailUtils() {}

    private static class SingletonHolder {
        private static final EmailUtils INSTANCE = new EmailUtils();
    }

    public static EmailUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String sendEmail(String toEmail, String title, String content) {
        try {
            String host = "smtp.163.com";
            String myEmail = "qhdsoftware@163.com";
            
            // 从 EnvCfgUtil 缓存读取并解密
            String emailPassEncrypted = EnvCfgUtil.getValByCfgName("EMAIL_PASS");
            String myPassword = Base64Utils.diyDecrypt(emailPassEncrypted);

            HtmlEmail email = new HtmlEmail();
            email.setHostName(host);
            email.setAuthenticator(new DefaultAuthenticator(myEmail, myPassword));
            email.setSSLOnConnect(true);
            email.setSmtpPort(465);
            email.setFrom(myEmail, "NorthPark官方");
            email.setSubject(title);
            email.addTo(toEmail, "用户");
            email.setCharset("utf-8");
            email.setHtmlMsg("<html><body>" + content + "</body></html>");
            
            String result = email.send();
            log.info("Email sent successfully to {}", toEmail);
            return result;
        } catch (Exception e) {
            log.error("Email sending failed", e);
            return "send error";
        }
    }
}