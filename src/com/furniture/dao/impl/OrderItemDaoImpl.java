package com.furniture.dao.impl;

import com.furniture.dao.BasicDAO;
import com.furniture.dao.OrderItemDao;
import com.furniture.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemDaoImpl extends BasicDAO<OrderItem> implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO orderItem(id,name,count,price,total_price,order_id) VALUES(?,?,?,?,?,?)";
        return update(sql, orderItem.getId(), orderItem.getName(), orderItem.getCount(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItemByOrderId(String orderId) {
        String sql = "SELECT id, name, count, price, total_price totalPrice, order_id orderId FROM orderItem WHERE order_id = ?";
        return queryMulti(sql, OrderItem.class, orderId);
    }

    @Override
    public int queryTotalFurnCountByOrderId(String orderId) {
        String sql = "SELECT SUM(count) FROM orderItem WHERE order_id = ?";
        Number count = (Number) queryScalar(sql, orderId);
        return count != null ? count.intValue() : 0;
    }

    @Override
    public BigDecimal queryTotalPriceByOrderId(String orderId) {
        String sql = "SELECT SUM(total_price) FROM orderItem WHERE order_id = ?";
        return (BigDecimal) queryScalar(sql, orderId);
    }


}
