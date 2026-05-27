package com.furniture.service;

import com.furniture.dao.OrderDao;
import com.furniture.dao.impl.OrderDaoImpl;
import com.furniture.entity.Cart;
import com.furniture.entity.Order;
import com.furniture.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    public String saveOrder(Cart cart, Integer memberId);
    public List<Order> queryOrderListByMemberId(Integer memberId);
    public List<OrderItem> queryOrderItemsByOrderId(String orderId);
    public int queryTotalFurnCountByOrderId(String orderId);
    public BigDecimal queryTotalPriceByOrderId(String orderId);
}
