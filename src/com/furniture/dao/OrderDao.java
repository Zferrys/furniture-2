package com.furniture.dao;

import com.furniture.entity.Order;

import java.util.List;

public interface OrderDao {
    public int saveOrder(Order order);
    public List<Order> queryOrderListByMemberId(Integer memberId);
    
    // 订单状态管理
    public int updateOrder(Order order);
    public Order queryOrderById(String orderId);
    public List<Order> queryAllOrders();
    
    // 分页查询
    public List<Order> queryOrdersByMemberIdByPage(Integer memberId, int begin, int pageSize);
    public int queryOrderCountByMemberId(Integer memberId);
    public List<Order> queryAllOrdersByPage(int begin, int pageSize);
    public int queryTotalOrderCount();
}

