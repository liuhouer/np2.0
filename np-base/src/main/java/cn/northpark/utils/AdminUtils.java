package cn.northpark.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 管理员权限判断工具类
 *
 * @author bruce
 */
public class AdminUtils {

    private static final Set<String> ADMIN_EMAILS = new HashSet<>();

    static {
        try {
            String emailsStr = EnvCfgUtil.getValByCfgName("admin_emails");
            if (StringUtils.isNotEmpty(emailsStr)) {
                String[] emails = emailsStr.split(",");
                for (String email : emails) {
                    if (StringUtils.isNotEmpty(email)) {
                        ADMIN_EMAILS.add(email.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            // Fallback default admin emails if bundle configuration is missing
            ADMIN_EMAILS.addAll(Arrays.asList("654714226@qq.com"));
        }
    }

    /**
     * 判断是否是管理员
     *
     * @param email 邮箱地址
     * @return true如果是管理员，否则false
     */
    public static boolean isAdmin(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        return ADMIN_EMAILS.contains(email.trim().toLowerCase());
    }
}
