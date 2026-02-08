package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.Permission;
import com.zoho.rbac_access_control.entities.Role;
import com.zoho.rbac_access_control.enums.ActionType;
import com.zoho.rbac_access_control.enums.ScopeType;
import com.zoho.rbac_access_control.repositories.PermissionRepository;
import com.zoho.rbac_access_control.services.PermissionService;
import com.zoho.rbac_access_control.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleService roleService;

    public PermissionServiceImpl(PermissionRepository permissionRepository, RoleService roleService){
        this.permissionRepository = permissionRepository;
        this.roleService = roleService;
    }

    public Permission createPermission(Integer roleId, String resource, ActionType action, ScopeType scope, String fieldName){
        Role role = roleService.getRoleById(roleId);

        Permission permission = new Permission();
        permission.setRole(role);
        permission.setResource(resource);
        permission.setAction(action);
        permission.setScope(scope);

        if(scope == ScopeType.FIELD){
            if(fieldName == null || fieldName.isEmpty()){
                throw new RuntimeException("FieldName is required when scope is Field Level");
            }
            permission.setFieldName(fieldName);
        } else{
            permission.setFieldName(null);
        }
        return permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions(){
        return permissionRepository.findAll();
    }
}
