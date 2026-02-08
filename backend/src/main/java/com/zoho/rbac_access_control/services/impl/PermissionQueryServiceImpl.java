package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.dto.FieldPermissionDto;
import com.zoho.rbac_access_control.dto.MePermissionsResponse;
import com.zoho.rbac_access_control.dto.ResourcePermissionDto;
import com.zoho.rbac_access_control.dto.TablePermissionDto;
import com.zoho.rbac_access_control.entities.Permission;
import com.zoho.rbac_access_control.entities.Role;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.enums.ActionType;
import com.zoho.rbac_access_control.enums.ScopeType;
import com.zoho.rbac_access_control.repositories.PermissionRepository;
import com.zoho.rbac_access_control.services.PermissionQueryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionQueryServiceImpl implements PermissionQueryService {

    private final PermissionRepository permissionRepository;

    public PermissionQueryServiceImpl(PermissionRepository permissionRepository){
        this.permissionRepository = permissionRepository;
    }

    @Override
    public MePermissionsResponse getPermissionsForUser(User user) {
        List<Integer> roleIds = user.getRoles().stream()
                .map(Role::getId).toList();

        List<Permission> permissions = permissionRepository.findByRole_IdIn(roleIds);

        MePermissionsResponse response = new MePermissionsResponse();
        Map<String, ResourcePermissionDto> resourceMap = new HashMap<>();

        for(Permission p : permissions){
            String resource = p.getResource();

            resourceMap.putIfAbsent(resource, new ResourcePermissionDto(new TablePermissionDto(false, false)));

            ResourcePermissionDto resourcePermission = resourceMap.get(resource);

            if(p.getScope() == ScopeType.TABLE){
                if(p.getAction() == ActionType.VIEW){
                    resourcePermission.getTable().setView(true);
                }

                if(p.getAction() == ActionType.EDIT){
                    resourcePermission.getTable().setEdit(true);
                }
            }

            if(p.getScope() == ScopeType.FIELD && p.getFieldName() != null){
                String fieldName = p.getFieldName();
                resourcePermission.getFields().putIfAbsent(fieldName, new FieldPermissionDto(false, false));

                FieldPermissionDto fieldPermission = resourcePermission.getFields().get(fieldName);
                if(p.getAction() == ActionType.VIEW){
                    fieldPermission.setView(true);
                }

                if(p.getAction() == ActionType.EDIT){
                    fieldPermission.setEdit(true);
                }
            }
        }
        response.setPermissions(resourceMap);
        return response;
    }
}
