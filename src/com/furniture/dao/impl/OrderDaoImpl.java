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
        String sql = "SELECT id, create_time createTime, price, status, member_id memberId FROM `order` WHERE member_id = ? ORDER BY create_time DESC";
        return queryMulti(sql, Order.class, memberId);
    }

    @Override
    public int updateOrder(Order order) {
        String sql = "UPDATE `order` SET status = ? WHERE id = ?";
        return update(sql, order.getStatus(), order.getId());
    }

    @Override
    public Order queryOrderById(String orderId) {
        String sql = "SELECT id, create_time createTime, price, status, member_id memberId FROM `order` WHERE id = ?";
        return querySingle(sql, Order.class, orderId);
    }

    @Override
    public List<Order> queryAllOrders() {
        String sql = "SELECT id, create_time createTime, price, status, member_id memberId FROM `order` ORDER BY create_time DESC";
        return queryMulti(sql, Order.class);
    }
    
    @Override
    public List<Order> queryOrdersByMemberIdByPage(Integer memberId, int begin, int pageSize) {
        String sql = "SELECT id, create_time createTime, price, status, member_id memberId FROM `order` WHERE member_id = ? ORDER BY create_time DESC LIMIT ?, ?";
        return queryMulti(sql, Order.class, memberId, begin, pageSize);
    }
    
    @Override
    public int queryOrderCountByMemberId(Integer memberId) {
        String sql = "SELECT COUNT(*) FROM `order` WHERE member_id = ?";
        return ((Number) queryScalar(sql, memberId)).intValue();
    }
    
    @Override
    public List<Order> queryAllOrdersByPage(int begin, int pageSize) {
        String sql = "SELECT id, create_time createTime, price, status, member_id memberId FROM `order` ORDER BY create_time DESC LIMIT ?, ?";
        return queryMulti(sql, Order.class, begin, pageSize);
    }
    
    @Override
    public int queryTotalOrderCount() {
        String sql = "SELECT COUNT(*) FROM `order`";
        return ((Number) queryScalar(sql)).intValue();
    }
}

