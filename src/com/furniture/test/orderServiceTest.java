package com.furniture.test;

import com.furniture.entity.Cart;
import com.furniture.entity.CartItem;
import com.furniture.service.OrderService;
import com.furniture.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class orderServiceTest {

    private OrderService orderService = new OrderServiceImpl();

    @Test
    public void testSaveOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(18,"元神之家",new BigDecimal(600),1,new BigDecimal(600),"assets/images/product-image/default.jpg"));
        cart.addItem(new CartItem(17,"hhh",new BigDecimal(60),2,new BigDecimal(120),"assets/images/product-image/default.jpg"));
        String orderId = orderService.saveOrder(cart, 2);
        System.out.println(orderId);
    }

    @Test
    public void testQueryOrderListByMemberId() {
        orderService.queryOrderListByMemberId(2).forEach(System.out::println);
    }
}
