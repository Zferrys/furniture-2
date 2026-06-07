package com.furniture.service;

import com.furniture.entity.Cart;
import com.furniture.entity.Order;
import com.furniture.entity.OrderItem;
import com.furniture.entity.Page;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    public String saveOrder(Cart cart, Integer memberId);
    public List<Order> queryOrderListByMemberId(Integer memberId);
    public List<Order> queryOrderListByAdminId();
    public List<OrderItem> queryOrderItemsByOrderId(String orderId);
    public int queryTotalFurnCountByOrderId(String orderId);
    public BigDecimal queryTotalPriceByOrderId(String orderId);
    
    // 订单状态管理
    public int updateOrderStatus(String orderId, Integer status);
    public Order queryOrderById(String orderId);
    
    // 分页查询
    public Page<Order> queryOrdersByMemberIdByPage(Integer memberId, int pageNo, int pageSize);
    public Page<Order> queryAllOrdersByPage(int pageNo, int pageSize);
}

