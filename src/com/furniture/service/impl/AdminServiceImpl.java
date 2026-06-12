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
        Admin admin = adminDao.queryAdminByName(name);
        if (admin == null) {
            return false;
        }
        String stored = admin.getPsd();
        // 新格式 SHA-256 + 盐值
        if (PasswordUtils.checkPassword(pwd, stored)) {
            return true;
        }
        // 兼容旧格式 MD5（32位十六进制）
        if (stored != null && stored.length() == 32 && PasswordUtils.isMd5Hex(stored)) {
            String md5 = PasswordUtils.md5Hex(pwd);
            if (stored.equalsIgnoreCase(md5)) {
                // 自动升级为新格式
                adminDao.updatePsd(name, PasswordUtils.hashPassword(pwd));
                return true;
            }
        }
        return false;
    }

    @Override
    public Admin queryAdminByName(String name) {
        return adminDao.queryAdminByName(name);
    }
}
