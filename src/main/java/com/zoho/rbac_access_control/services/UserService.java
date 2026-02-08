package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.User;

import java.util.List;

public interface UserService {
    User getByUsername(String username);
    User createUser(String username, String password);
    User getById(Integer id);
    List<User> getAllUsers();
    User assignRole(Integer userId, Integer roleId);
}
