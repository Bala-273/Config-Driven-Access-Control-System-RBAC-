package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.User;

public interface AuthService {
    User register(String username, String password);
    User login(String username, String password);
}
