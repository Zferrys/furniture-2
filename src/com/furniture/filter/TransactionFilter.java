package com.furniture.filter;

import com.furniture.utils.JDBCUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

public class TransactionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JDBCUtilsByDruid.commit();
        } catch (Exception e) {
            JDBCUtilsByDruid.rollback();
            throw new RuntimeException(e);
        } finally {
            // 确保请求结束后清理ThreadLocal连接
            JDBCUtilsByDruid.clearConnection();
        }
    }

    @Override
    public void destroy() {

    }
}
