package com.serra.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static Cookie getCookie(HttpServletRequest request, String nombre) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (nombre.equals(c.getName())) {
                    return c;
                }
            }
        }
        return null;
    }

    public static boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String nombre) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (nombre.equals(c.getName())) {
                    c.setMaxAge(0);
                    response.addCookie(c);
                    return true;
                }
            }
        }
        return false;
    }
}