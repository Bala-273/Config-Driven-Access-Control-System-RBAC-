package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.Role;

import java.util.List;

public interface RoleService {
    Role createRole(String roleName);
    List<Role> getAllRoles();
    Role getRoleById(Integer id);
}
