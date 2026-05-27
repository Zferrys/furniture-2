package com.furniture.dao;

import com.furniture.entity.Order;

import java.util.List;

public interface OrderDao {
    public int saveOrder(Order order);
    public List<Order> queryOrderListByMemberId(Integer memberId);
}
