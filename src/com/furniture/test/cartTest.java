package com.furniture.test;

import com.furniture.entity.Cart;
import com.furniture.entity.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

public class cartTest {
    Cart cart = new Cart();

    @Test

    public void addItem() {
        cart.addItem(new CartItem(18,"元神之家",new BigDecimal(60),1,new BigDecimal(60)));
        cart.addItem(new CartItem(17,"元神之家",new BigDecimal(60),1,new BigDecimal(60)));
        System.out.println(cart);
    }
}
