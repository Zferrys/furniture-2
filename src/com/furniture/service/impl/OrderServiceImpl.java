package com.furniture.service.impl;

import com.furniture.dao.FurnDao;
import com.furniture.dao.OrderDao;
import com.furniture.dao.OrderItemDao;
import com.furniture.dao.impl.FurnDaoImpl;
import com.furniture.dao.impl.OrderDaoImpl;
import com.furniture.dao.impl.OrderItemDaoImpl;
import com.furniture.entity.*;
import com.furniture.service.OrderService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    OrderItemDao orderItemDao = new OrderItemDaoImpl();
    FurnDao furnDao = new FurnDaoImpl();

    @Override
    public String saveOrder(Cart cart, Integer memberId) {
        String orderId = null;
        try {
            // 使用 UUID 生成不可预测的订单号
            orderId = UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
            Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, memberId);
            orderDao.saveOrder(order);

            Map<Integer, CartItem> items = cart.getItems();
            for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
                CartItem cartItem = entry.getValue();
                OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
                orderItemDao.saveOrderItem(orderItem);

                // 原子更新库存和销量，防止并发超卖
                Furn furn = furnDao.queryFurnById(cartItem.getId());
                if (furn == null) {
                    throw new RuntimeException("商品不存在: " + cartItem.getId());
                }
                if (furn.getStore() < cartItem.getCount()) {
                    throw new RuntimeException("库存不足: " + furn.getName());
                }
                int affectedRows = furnDao.updateStockAtomic(cartItem.getId(), cartItem.getCount(), cartItem.getCount());
                if (affectedRows == 0) {
                    throw new RuntimeException("库存不足，无法下单: " + cartItem.getName());
                }
            }

            cart.clear();
        } catch (Exception e) {
            throw new RuntimeException("订单创建失败: " + e.getMessage(), e);
        }
        return orderId;
    }

    @Override
    public List<Order> queryOrderListByMemberId(Integer memberId) {
        return orderDao.queryOrderListByMemberId(memberId);
    }

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(String orderId) {
        return orderItemDao.queryOrderItemByOrderId(orderId);
    }

    @Override
    public int queryTotalFurnCountByOrderId(String orderId) {
        return orderItemDao.queryTotalFurnCountByOrderId(orderId);
    }

    @Override
    public BigDecimal queryTotalPriceByOrderId(String orderId) {
        return orderItemDao.queryTotalPriceByOrderId(orderId);
    }

    @Override
    public int updateOrderStatus(String orderId, Integer status) {
        Order order = queryOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在: " + orderId);
        }
        order.setStatus(status);
        return orderDao.updateOrder(order);
    }

    @Override
    public Order queryOrderById(String orderId) {
        return orderDao.queryOrderById(orderId);
    }

    @Override
    public List<Order> queryOrderListByAdminId() {
        return orderDao.queryAllOrders();
    }
    
    @Override
    public Page<Order> queryOrdersByMemberIdByPage(Integer memberId, int pageNo, int pageSize) {
        int totalRow = orderDao.queryOrderCountByMemberId(memberId);
        int pageTotalCount = (totalRow + pageSize - 1) / pageSize;
        int begin = (pageNo - 1) * pageSize;
        List<Order> items = orderDao.queryOrdersByMemberIdByPage(memberId, begin, pageSize);
        Page<Order> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRow(totalRow);
        page.setPageTotalCount(pageTotalCount);
        page.setItems(items);
        return page;
    }
    
    @Override
    public Page<Order> queryAllOrdersByPage(int pageNo, int pageSize) {
        int totalRow = orderDao.queryTotalOrderCount();
        int pageTotalCount = (totalRow + pageSize - 1) / pageSize;
        int begin = (pageNo - 1) * pageSize;
        List<Order> items = orderDao.queryAllOrdersByPage(begin, pageSize);
        Page<Order> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalRow(totalRow);
        page.setPageTotalCount(pageTotalCount);
        page.setItems(items);
        return page;
    }
}

