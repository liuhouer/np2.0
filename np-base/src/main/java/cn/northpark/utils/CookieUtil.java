package cn.northpark.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018/4/14 
 *
 */
public class CookieUtil {

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,
                             String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }

        return null;
    }
    
    
    
    /**
     * 清空cookie
     * @param request
     * @param response
     */
    public static void clearAll(HttpServletRequest request,HttpServletResponse response) {
    	Cookie[] cookies = request.getCookies();
    	if (cookies != null) {
    		for (Cookie cookie: cookies) {
    			 Cookie cookie1 = new Cookie(cookie.getName(),null); 
    			 cookie1.setMaxAge(0); 
    			 cookie1.setPath("/"); 
    			 response.addCookie(cookie1); 
    		}
    	}

    }
    
    /**
     * 将cookie封装成Map
     * @param request
     * @return
     */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }


    /**
     * 将cookie+请求UA封装成Map
     * @param request
     * @return
     */
    public static Map<String, String> readCookieUA(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        String userAgent = request.getHeader("user-agent");
        cookieMap.put("UA",userAgent);
        return cookieMap;
    }

}
