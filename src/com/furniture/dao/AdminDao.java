package com.furniture.dao;

import com.furniture.entity.Admin;

public interface AdminDao {
    public boolean queryAdmin(String name, String pwd);
    public Admin queryAdminByName(String name);
}
