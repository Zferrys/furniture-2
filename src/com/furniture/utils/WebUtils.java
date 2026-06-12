package com.furniture.utils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class WebUtils {
    public static String FURN_IMG_DIRECTORY = "assets/images/product-image";
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(requestedWithHeader);
    }
    public static String YearMonthDay(){
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();
        String YearMonthDay = year + "/" + monthValue + "/" + dayOfMonth;
        return YearMonthDay;
    }

    /**
     * 安全的重定向 URL：仅允许同源跳转，防止开放重定向攻击
     */
    public static String getSafeRedirectUrl(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            return request.getContextPath() + "/index.jsp";
        }
        try {
            String targetOrigin = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath();
            if (referer.startsWith(targetOrigin)) {
                return referer;
            }
        } catch (Exception e) {
            // 解析失败时回退到首页
        }
        return request.getContextPath() + "/index.jsp";
    }
}
