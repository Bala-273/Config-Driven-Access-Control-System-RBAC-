package com.zoho.rbac_access_control.dto;

import java.util.HashMap;
import java.util.Map;

public class MePermissionsResponse {

    // key = resource name (employees/projects/orders)
    private Map<String, ResourcePermissionDto> permissions = new HashMap<>();

    public MePermissionsResponse() {}

    public Map<String, ResourcePermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, ResourcePermissionDto> permissions) {
        this.permissions = permissions;
    }
}
