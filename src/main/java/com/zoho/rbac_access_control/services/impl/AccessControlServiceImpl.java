package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.Permission;
import com.zoho.rbac_access_control.entities.Role;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.enums.ActionType;
import com.zoho.rbac_access_control.enums.ScopeType;
import com.zoho.rbac_access_control.repositories.PermissionRepository;
import com.zoho.rbac_access_control.services.AccessControlService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessControlServiceImpl implements AccessControlService {
    private final PermissionRepository permissionRepository;

    public AccessControlServiceImpl(PermissionRepository permissionRepository){
        this.permissionRepository = permissionRepository;
    }

    private List<Permission> getUserPermissions(User user){
        List<Integer> roleIds = user.getRoles()
                .stream().map(Role::getId).toList();
        return permissionRepository.findByRoleIdIn(roleIds);
    }

    private boolean hasPermission(User user, String resource, ActionType action, ScopeType scope, String fieldName){
        List<Permission> permissions = getUserPermissions(user);
        return permissions.stream().anyMatch(p ->
                p.getResource().equalsIgnoreCase(resource)
                && p.getAction() == action
                && p.getScope() == scope
                && (
                        scope == ScopeType.TABLE ||
                                (p.getFieldName() != null && p.getFieldName().equalsIgnoreCase(fieldName))
                        )

                );
    }

    public boolean canViewTable(User user, String resource){
        return hasPermission(user, resource, ActionType.VIEW, ScopeType.TABLE, null);
    }

    public boolean canEditTable(User user, String resource){
        return hasPermission(user, resource, ActionType.EDIT, ScopeType.TABLE, null);
    }

    public boolean canEditField(User user, String resource, String fieldName){
        return hasPermission(user, resource, ActionType.EDIT, ScopeType.FIELD, fieldName);
    }

    public boolean canViewField(User user, String resource, String fieldName){
        return hasPermission(user, resource, ActionType.VIEW, ScopeType.FIELD ,fieldName);
    }
}
