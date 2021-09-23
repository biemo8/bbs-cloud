package com.biemo.cloud.auth.modular.sso.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 *
 *
 * @Date 2019-09-27 19:33
 */
public final class CookieUtil {

    /**
     * 获取Cookie
     *
     *
     * @Date 2019-09-27 19:33
     */
    public static String get(HttpServletRequest request, String key) {
        String ticket = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    ticket = cookie.getValue();
                }
            }
        }
        return ticket;
    }

    /**
     * 设置cookie
     *
     *
     * @Date 2019-09-27 19:33
     */
    public static void set(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        set(response, cookie);
    }

    /**
     * 设置cookie
     *
     *
     * @Date 2019-09-27 19:33
     */
    public static void set(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     *
     * @Date 2019-09-27 19:33
     */
    public static void remove(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        remove(response, cookie);
    }

    /**
     * 删除cookie
     *
     *
     * @Date 2019-09-27 19:33
     */
    public static void remove(HttpServletResponse response, Cookie cookie) {
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


}
