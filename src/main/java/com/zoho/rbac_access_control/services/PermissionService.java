package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.Permission;
import com.zoho.rbac_access_control.enums.ActionType;
import com.zoho.rbac_access_control.enums.ScopeType;

import java.util.List;

public interface PermissionService {
    Permission createPermission(Integer roleId, String resource, ActionType action, ScopeType scope, String fieldName);
    List<Permission> getAllPermissions();
}
