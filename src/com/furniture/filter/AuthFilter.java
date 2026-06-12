package com.furniture.filter;

import com.furniture.entity.Admin;
import com.furniture.entity.Member;
import com.furniture.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthFilter implements Filter {

    private List<String> excludeUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludeUrlParam = filterConfig.getInitParameter("excludeUrls");
        if (excludeUrlParam != null && !excludeUrlParam.isEmpty()) {
            String[] urls = excludeUrlParam.split(",");
            excludeUrls = Arrays.asList(urls);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getServletPath();

        if (!WebUtils.isAjaxRequest(request)) {
            // 如果是登录页面，直接放行
            if (excludeUrls != null && excludeUrls.contains(url)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }


            // 如果访问路径包含 "manage"，需要检查管理员权限
            if (url.contains("/manage/")) {
                Admin admin = (Admin) request.getSession().getAttribute("admin");
                if (admin == null) {
                    request.getRequestDispatcher("/views/manage/manage_login.jsp").forward(servletRequest, servletResponse);
                    return;
                }
            } else {
                // 其他路径需要检查会员权限
                Member member = (Member) request.getSession().getAttribute("member");
                Admin admin = (Admin) request.getSession().getAttribute("admin");
                if (member == null && admin == null) {
                    System.out.println("未登录会员，跳转到会员登录页面");
                    request.getRequestDispatcher("/views/member/login.jsp").forward(servletRequest, servletResponse);
                    return;
                }
            }
            // 放行其他请求
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            Member member = (Member) request.getSession().getAttribute("member");
            Admin admin = (Admin) request.getSession().getAttribute("admin");
            // FIX: AJAX请求也需要检查 /manage/ 路径的管理员权限
            if (url.contains("/manage/")) {
                if (admin == null) {
                    Map<String, Object> resMap = new HashMap<>();
                    resMap.put("url", "views/manage/manage_login.jsp");
                    String resJson = new Gson().toJson(resMap);
                    servletResponse.getWriter().write(resJson);
                    return;
                }
            } else if (member == null && admin == null) {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("url", "views/member/login.jsp");
                String resJson = new Gson().toJson(resMap);
                servletResponse.getWriter().write(resJson);
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }


    }

    @Override
    public void destroy() {

    }
}
