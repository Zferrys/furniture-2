package com.furniture.web;

import com.furniture.entity.Admin;
import com.furniture.service.AdminService;
import com.furniture.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

public class adminServlet extends Basic_Servlet {
    AdminService adminService = new AdminServiceImpl();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String psd = req.getParameter("psd");
        
        if (name == null || name.isEmpty() || psd == null || psd.isEmpty()) {
            req.setAttribute("msg", "用户名或密码不能为空");
            req.getRequestDispatcher("/views/manage/manage_login.jsp").forward(req, resp);
            return;
        }
        
        Admin admin = adminService.queryAdminByName(name);
        // FIX: 使用用户输入的 name 和 psd，而非数据库查出的值（否则任意用户名即可绕过密码验证）
        if (admin != null && adminService.login(name, psd)) {
            // Session固定攻击防护：先销毁旧session再创建新session
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("admin", admin);
            // 重建 CSRF token（旧 session 销毁后 token 丢失）
            byte[] tokenBytes = new byte[32];
            new SecureRandom().nextBytes(tokenBytes);
            session.setAttribute("csrfToken", Base64.getEncoder().encodeToString(tokenBytes));
            resp.sendRedirect(req.getContextPath() + "/views/manage/manage_menu.jsp");
        } else {
            req.setAttribute("msg", "用户名或密码错误");
            req.getRequestDispatcher("/views/manage/manage_login.jsp").forward(req, resp);
        }
    }


    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

}
