package com.furniture.test;

import com.furniture.entity.Furn;
import com.furniture.entity.Page;
import com.furniture.service.FurnService;
import com.furniture.service.impl.FurnServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zph
 * @version 1.0
 */
public class furnServiceTest {
    private final FurnService furnService = new FurnServiceImpl();
    @Test
    public void TestService(){
        List<Furn> furns = furnService.showFurns();
        furns.forEach(System.out::println);
    }
    @Test
    public void TestClose(){
        int i = furnService.closeFurnById(5);
        System.out.println(i);
    }

    @Test
    public void TestAdd(){
        Furn furn = new Furn(null,"111","元神",new BigDecimal("22.5"),8,10,"assets/images/product-image/default.jpg");
        int i = furnService.addFurn(furn);
        System.out.println(i);
    }
    @Test
    public void TestUpdate(){
        Furn furn = new Furn(14,"222","提纳里",new BigDecimal("25.5"),5,10,"assets/images/product-image/default.jpg");
        int i = furnService.updateFurn(furn);
        System.out.println(i);
    }

    @Test
    public void TestPageItems(){
        Page<Furn> page = furnService.getPageItems(1, 2);
        System.out.println(page);
    }

    @Test
    public void TestPageItemsByName(){
        Page<Furn> page = furnService.getPageItemsByName(1, 2, "元");
        System.out.println(page);
    }
}
