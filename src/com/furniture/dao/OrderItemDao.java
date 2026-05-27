package com.furniture.dao;

import com.furniture.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemDao {
    public int saveOrderItem(OrderItem orderItem);
    public List<OrderItem> queryOrderItemByOrderId(String orderId);
    public int queryTotalFurnCountByOrderId(String orderId);
    public BigDecimal queryTotalPriceByOrderId(String orderId);
}
