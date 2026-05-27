package com.furniture.test;

import com.furniture.dao.OrderItemDao;
import com.furniture.dao.impl.OrderItemDaoImpl;
import com.furniture.entity.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderItemDaoTest {
    OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    public void testSaveOrderItem() {
        OrderItem chair = new OrderItem(null, "Chair", 2, new BigDecimal(49.99), new BigDecimal(99.98), "1");
        System.out.println(orderItemDao.saveOrderItem(chair));
    }

    @Test
    public void testQueryOrderItemByOrderId() {
        orderItemDao.queryOrderItemByOrderId("17621751486012").forEach(System.out::println);
    }
}
