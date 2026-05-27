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
}
