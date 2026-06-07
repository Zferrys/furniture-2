package com.furniture.web;

import com.furniture.entity.Cart;
import com.furniture.entity.CartItem;
import com.furniture.entity.Furn;
import com.furniture.service.FurnService;
import com.furniture.service.impl.FurnServiceImpl;
import com.furniture.utils.DataUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class cartServlet extends Basic_Servlet {
    FurnService furnService = new FurnServiceImpl();

    protected void addCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.getFurnById(id);
        //
        if (furn == null || furn.getStore() <= 0) {
            req.setAttribute("msg", "库存不足，无法添加到购物车");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        CartItem cartItem = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice(), furn.getImgPath());
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null == cart) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        resp.sendRedirect(req.getHeader("Referer"));
    }

    protected void addCartItemByAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.getFurnById(id);
        //
        if (furn == null || furn.getStore() <= 0) {
            req.setAttribute("msg", "库存不足，无法添加到购物车");
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }

        CartItem cartItem = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice(), furn.getImgPath());
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null == cart) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("totalCount", cart.getTotalCount());
        String resJson = new Gson().toJson(resMap);
        resp.getWriter().write(resJson);
    }


    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        int count = DataUtils.parseInt(req.getParameter("count"), 1);
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.updateCount(id, count);
        }
        resp.sendRedirect(req.getHeader("Referer"));
    }

    protected void deleteCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.deleteItem(id);
        }
        resp.sendRedirect(req.getHeader("Referer"));
    }


    protected void clearCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null != cart) {
            cart.clear();
        }
        resp.sendRedirect(req.getHeader("Referer"));
    }
}
