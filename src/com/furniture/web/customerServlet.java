package com.furniture.web;

import com.furniture.entity.Furn;
import com.furniture.entity.Page;
import com.furniture.service.FurnService;
import com.furniture.service.impl.FurnServiceImpl;
import com.furniture.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class customerServlet extends Basic_Servlet {
    private final FurnService furnService = new FurnServiceImpl();

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int begin = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        Page<Furn> page = furnService.getPageItems(begin, pageSize);
        req.setAttribute("page", page);
        req.getRequestDispatcher("views/customer/index.jsp").forward(req, resp);

    }

    protected void searchByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int begin = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        String name = req.getParameter("name");
        StringBuilder url = new StringBuilder("customer?action=searchByName&");
        if (name != null && !name.isEmpty()) {
            url.append("name=" + name + "&");
        } else {
            name = "";
        }
        Page<Furn> page = furnService.getPageItemsByName(begin, pageSize, name);
        page.setUrl(url.toString());
        req.setAttribute("page", page);
        req.getRequestDispatcher("views/customer/index.jsp").forward(req, resp);

    }


}
