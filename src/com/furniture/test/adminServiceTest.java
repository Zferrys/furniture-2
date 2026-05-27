package com.furniture.test;

import com.furniture.service.AdminService;
import com.furniture.service.impl.AdminServiceImpl;
import org.junit.Test;

public class adminServiceTest {
    AdminService adminService = new AdminServiceImpl();
    @Test
    public void testLogin(){
        System.out.println(adminService.login("zph","1978738217"));
    }
}
