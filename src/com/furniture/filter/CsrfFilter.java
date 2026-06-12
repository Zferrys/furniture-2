package com.furniture.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;

/**
 * CSRF 防护过滤器
 * - GET 请求：自动生成 token 并存入 session
 * - POST 请求：校验 csrfToken 参数（只读操作豁免）
 */
public class CsrfFilter implements Filter {

    /** 只读操作，无需 CSRF 校验 */
    private static final Set<String> EXEMPT_ACTIONS = Set.of(
        "page", "searchByName", "detail", "exitUsername", "exitCode"
    );

    /** 安全的 HTTP 方法，不触发状态变更 */
    private static final Set<String> SAFE_METHODS = Set.of("GET", "HEAD", "OPTIONS");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // GET/HEAD/OPTIONS 等安全方法：生成 token 并放行
        if (SAFE_METHODS.contains(req.getMethod().toUpperCase())) {
            ensureToken(session);
            chain.doFilter(request, response);
            return;
        }

        // POST 请求：检查 action 是否为豁免操作
        String action = req.getParameter("action");
        if (action != null && EXEMPT_ACTIONS.contains(action)) {
            ensureToken(session);
            chain.doFilter(request, response);
            return;
        }

        // 校验 CSRF token
        String sessionToken = (String) session.getAttribute("csrfToken");
        String requestToken = req.getParameter("csrfToken");

        if (sessionToken == null || !sessionToken.equals(requestToken)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token 校验失败");
            return;
        }

        chain.doFilter(request, response);
    }

    private void ensureToken(HttpSession session) {
        if (session.getAttribute("csrfToken") == null) {
            byte[] tokenBytes = new byte[32];
            new SecureRandom().nextBytes(tokenBytes);
            session.setAttribute("csrfToken", Base64.getEncoder().encodeToString(tokenBytes));
        }
    }

    @Override
    public void destroy() {}
}
