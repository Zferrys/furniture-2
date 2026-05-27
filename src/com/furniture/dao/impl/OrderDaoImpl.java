package com.furniture.dao.impl;

import com.furniture.dao.BasicDAO;
import com.furniture.dao.OrderDao;
import com.furniture.entity.Order;

import java.util.List;

public class OrderDaoImpl extends BasicDAO<Order> implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO `order`(id,create_time,price,status,member_id) VALUES(?,?,?,?,?)";
        return update(sql, order.getId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getMemberId());
    }

    @Override
    public List<Order> queryOrderListByMemberId(Integer memberId) {
        String sql = "SELECT id, create_time createTime, price, status, member_id memberId FROM `order` WHERE member_id = ?";
        return queryMulti(sql, Order.class, memberId);
    }
}
