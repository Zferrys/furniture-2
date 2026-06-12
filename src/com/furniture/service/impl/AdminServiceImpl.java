package com.furniture.service.impl;

import com.furniture.dao.AdminDao;
import com.furniture.dao.impl.AdminDaoImpl;
import com.furniture.entity.Admin;
import com.furniture.service.AdminService;
import com.furniture.utils.PasswordUtils;

public class AdminServiceImpl implements AdminService {
    private final AdminDao adminDao = new AdminDaoImpl();
    @Override
    public boolean login(String name, String pwd) {
        // 先用用户名查出管理员，再用 PasswordUtils 校验密码（替代原来的明文SQL比较）
        Admin admin = adminDao.queryAdminByName(name);
        if (admin == null) {
            return false;
        }
        return PasswordUtils.checkPassword(pwd, admin.getPsd());
    }

    @Override
    public Admin queryAdminByName(String name) {
        return adminDao.queryAdminByName(name);
    }
}
