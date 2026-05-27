package com.furniture.service.impl;

import com.furniture.dao.AdminDao;
import com.furniture.dao.impl.AdminDaoImpl;
import com.furniture.entity.Admin;
import com.furniture.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private final AdminDao adminDao = new AdminDaoImpl();
    @Override
    public boolean login(String name, String pwd) {
        return adminDao.queryAdmin(name, pwd);
    }

    @Override
    public Admin queryAdminByName(String name) {
        return adminDao.queryAdminByName(name);
    }
}
