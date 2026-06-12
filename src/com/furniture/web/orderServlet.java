package com.furniture.web;

import com.furniture.entity.*;
import com.furniture.service.FurnService;
import com.furniture.service.OrderService;
import com.furniture.service.impl.FurnServiceImpl;
import com.furniture.service.impl.OrderServiceImpl;
import com.furniture.utils.DataUtils;

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
            if (furn == null) {
                req.setAttribute("msg", "商品不存在，无法下单: ID=" + item.getId());
                resp.sendRedirect(req.getContextPath() + "/views/cart/cart.jsp");
                return;
            }
            if (furn.getStore() < item.getCount()) {
                req.setAttribute("msg", "库存不足，无法下单: " + furn.getName() + "（剩余" + furn.getStore() + "件）");
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

        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        
        Page<Order> page;
        if (admin != null) {
            page = orderService.queryAllOrdersByPage(pageNo, pageSize);
        } else {
            page = orderService.queryOrdersByMemberIdByPage(member.getId(), pageNo, pageSize);
        }
        page.setUrl("orderServlet?action=orderManager&");
        req.setAttribute("page", page);
        req.getRequestDispatcher("views/order/order.jsp").forward(req, resp);
    }


    protected void orderItemDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        if (orderId == null || orderId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // IDOR 防护：校验订单归属（管理员可查看所有，普通用户只能查看自己的）
        Member member = (Member) req.getSession().getAttribute("member");
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        Order order = orderService.queryOrderById(orderId);
        if (order == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        if (admin == null && (member == null || !member.getId().equals(order.getMemberId()))) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        List<OrderItem> orderItems = orderService.queryOrderItemsByOrderId(orderId);
        int totalCount = orderService.queryTotalFurnCountByOrderId(orderId);
        BigDecimal totalPrice = orderService.queryTotalPriceByOrderId(orderId);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("orderItems", orderItems);
        req.setAttribute("order", order);
        req.getRequestDispatcher("views/order/order_detail.jsp").forward(req, resp);
    }

    /**
     * 更新订单状态 (发货/完成)
     */
    protected void updateOrderStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        if (admin == null) {
            resp.sendRedirect(req.getContextPath() + "/views/manage/manage_login.jsp");
            return;
        }

        String orderId = req.getParameter("orderId");
        String statusStr = req.getParameter("status");
        
        if (orderId == null || orderId.isEmpty() || statusStr == null) {
            resp.sendRedirect(req.getContextPath() + "/orderServlet?action=orderManager");
            return;
        }

        int status = Integer.parseInt(statusStr);
        try {
            orderService.updateOrderStatus(orderId, status);
            req.setAttribute("msg", "订单状态更新成功");
        } catch (Exception e) {
            req.setAttribute("msg", "订单状态更新失败: " + e.getMessage());
        }
        
        resp.sendRedirect(req.getContextPath() + "/orderServlet?action=orderManager");
    }

    /**
     * 查看订单详情 (管理员)
     */
    protected void orderDetailByAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        if (admin == null) {
            resp.sendRedirect(req.getContextPath() + "/views/manage/manage_login.jsp");
            return;
        }

        String orderId = req.getParameter("orderId");
        if (orderId == null || orderId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/orderServlet?action=orderManager");
            return;
        }

        Order order = orderService.queryOrderById(orderId);
        if (order == null) {
            req.setAttribute("msg", "订单不存在");
            resp.sendRedirect(req.getContextPath() + "/orderServlet?action=orderManager");
            return;
        }

        List<OrderItem> orderItems = orderService.queryOrderItemsByOrderId(orderId);
        BigDecimal totalPrice = orderService.queryTotalPriceByOrderId(orderId);
        
        req.setAttribute("order", order);
        req.setAttribute("orderItems", orderItems);
        req.setAttribute("totalPrice", totalPrice);
        req.getRequestDispatcher("views/order/order_detail.jsp").forward(req, resp);
    }
}

