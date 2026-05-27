package com.furniture.test;

import com.furniture.dao.OrderDao;
import com.furniture.dao.impl.OrderDaoImpl;
import com.furniture.entity.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDaoTest {
    OrderDao orderDao = new OrderDaoImpl();
    @Test
    public void testSaveOrder() {
        Order order = new Order("1", new Date(), new BigDecimal(100), 0, 2);
        System.out.println(orderDao.saveOrder(order));
    }

    @Test
    public void testQueryOrderListByMemberId() {
        orderDao.queryOrderListByMemberId(2).forEach(System.out::println);
    }

}
