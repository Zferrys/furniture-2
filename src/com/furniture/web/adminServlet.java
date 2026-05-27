package com.furniture.web;

import com.furniture.entity.Admin;
import com.furniture.service.AdminService;
import com.furniture.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class adminServlet extends Basic_Servlet {
    AdminService adminService = new AdminServiceImpl();

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String psd = req.getParameter("psd");
        Admin admin = adminService.queryAdminByName(name);
        if (adminService.login(admin.getName(), admin.getPsd())) {
            req.getSession().setAttribute("admin", admin);
            resp.sendRedirect("/furniture/views/manage/manage_menu.jsp");
        } else {
            req.setAttribute("msg", "用户名或密码错误");
            req.setAttribute("name", admin.getName());
            req.getRequestDispatcher("/views/manage/manage_login.jsp").forward(req, resp);
        }
    }


    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

}
