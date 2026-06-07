package com.furniture.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    private Map<Integer, CartItem> items = new HashMap<>();
    private int totalCount = 0;
    private BigDecimal totalPrice = new BigDecimal(0);

    public Cart() {
    }

    public void deleteItem(int id) {
        items.remove(id);
    }

    public void clear() {
        items.clear();
    }

    public void updateCount(int id, int count) {
        CartItem item = items.get(id);
        if (null != item) {
            item.setCount(count);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }

    }

    public BigDecimal getTotalPrice() {
        totalPrice = BigDecimal.ZERO; // 重置总价
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            CartItem cartItem = items.get(id);
            if (null != cartItem) {
                totalPrice = totalPrice.add(cartItem.getTotalPrice());
            }
        }
        return totalPrice;
    }

    public void addItem(CartItem cartItem) {

        CartItem item = items.get(cartItem.getId());
        if (null == item) {
            items.put(cartItem.getId(), cartItem);
        } else {
            item.setCount(item.getCount() + 1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    public int getTotalCount() {
        totalCount = 0;
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            CartItem cartItem = items.get(id);
            if (null != cartItem) {
                totalCount += cartItem.getCount();
            }
        }
        return totalCount;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
