package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.dto.MePermissionsResponse;
import com.zoho.rbac_access_control.entities.User;

public interface PermissionQueryService {
    MePermissionsResponse getPermissionsForUser(User user);
}
