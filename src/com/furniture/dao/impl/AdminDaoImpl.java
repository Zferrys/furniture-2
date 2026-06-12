package com.furniture.dao.impl;

import com.furniture.dao.AdminDao;
import com.furniture.dao.BasicDAO;
import com.furniture.entity.Admin;

public class AdminDaoImpl extends BasicDAO<Admin> implements AdminDao {
    @Override
    public boolean queryAdmin(String name, String pwd) {
        String sql = "select id,name,psd from admin where name = ? and psd = ?";
        return querySingle(sql, Admin.class, name, pwd) != null;
    }

    @Override
    public Admin queryAdminByName(String name) {
        String sql = "select id,name,psd from admin where name = ?";
        return querySingle(sql, Admin.class, name);
    }

    @Override
    public int updatePsd(String name, String newPsd) {
        String sql = "update admin set psd = ? where name = ?";
        return update(sql, newPsd, name);
    }
}
