package com.furniture.web;

import com.furniture.entity.*;
import com.furniture.service.FurnService;
import com.furniture.service.OrderService;
import com.furniture.service.impl.FurnServiceImpl;
import com.furniture.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class orderServlet extends Basic_Servlet {

    private OrderService orderService = new OrderServiceImpl();
    private FurnService furnService = new FurnServiceImpl();

    protected void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Member member = (Member) req.getSession().getAttribute("member");
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        if (member == null && admin == null) {
            req.setAttribute("msg", "请先登录");
            resp.sendRedirect(req.getContextPath() + "/views/member/login.jsp");
            return;
        }

        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null || cart.getItems().size() == 0) {
            req.setAttribute("msg", "购物车为空，无法下单");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        for (CartItem item : cart.getItems().values()) {
            Furn furn = furnService.getFurnById(item.getId());
            if (furn.getStore() < item.getCount()) {
                req.setAttribute("msg", "库存不足，无法下单");
                resp.sendRedirect(req.getContextPath() + "/views/cart/cart.jsp");
                return;
            }
        }

        //保存订单
        String orderId;
        if (member != null) {
            orderId = orderService.saveOrder(cart, member.getId());
        } else {
            orderId = orderService.saveOrder(cart, admin.getId());
        }
        System.out.println("订单号：" + orderId);
        req.setAttribute("orderId", orderId);

        req.getRequestDispatcher("views/order/checkout.jsp").forward(req, resp);
    }


    /**
     * 订单管理
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void orderManager(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member member = (Member) req.getSession().getAttribute("member");
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        if (member == null && admin == null) {
            req.setAttribute("msg", "请先登录");
            resp.sendRedirect(req.getContextPath() + "/views/member/login.jsp");
            return;
        }

        List<Order> orders;
        if (member != null) {
            orders = orderService.queryOrderListByMemberId(member.getId());
        } else {
            orders = orderService.queryOrderListByMemberId(admin.getId());
        }
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("views/order/order.jsp").forward(req, resp);
    }


    protected void orderItemDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        if (orderId == null || orderId.isEmpty()) {
            req.setAttribute("msg", "订单号不能为空");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        List<OrderItem> orderItems = orderService.queryOrderItemsByOrderId(orderId);
        int totalCount = orderService.queryTotalFurnCountByOrderId(orderId);
        BigDecimal totalPrice = orderService.queryTotalPriceByOrderId(orderId);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("orderItems", orderItems);
        req.getRequestDispatcher("views/order/order_detail.jsp").forward(req, resp);
    }
}
