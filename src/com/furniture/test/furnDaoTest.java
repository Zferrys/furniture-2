package com.furniture.test;

import com.furniture.dao.impl.FurnDaoImpl;
import org.junit.Test;

/**
 * @author zph
 * @version 1.0
 */
public class furnDaoTest {
    @Test
    public void TestDao(){
        new FurnDaoImpl().queryFurns().forEach(System.out::println);
    }
}
