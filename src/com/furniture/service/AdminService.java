package com.furniture.service;

import com.furniture.entity.Admin;

public interface AdminService {
    public boolean login(String name, String pwd);
    public Admin queryAdminByName(String name);
}
