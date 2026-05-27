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


public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    OrderItemDao orderItemDao = new OrderItemDaoImpl();
    FurnDao furnDao = new FurnDaoImpl();

    @Override
    public String saveOrder(Cart cart, Integer memberId) {

        String orderId = System.currentTimeMillis() + "" + memberId;
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, memberId);
        orderDao.saveOrder(order);

        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer,CartItem> entry : items.entrySet()) {
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            orderItemDao.saveOrderItem(orderItem);

            //更新库存和销量
            Furn furn = furnDao.queryFurnById(cartItem.getId());
            furn.setSales(furn.getSales() + cartItem.getCount());
            furn.setStore(furn.getStore() - cartItem.getCount());
            furnDao.updateFurn(furn);
        }

        cart.clear();
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
}
