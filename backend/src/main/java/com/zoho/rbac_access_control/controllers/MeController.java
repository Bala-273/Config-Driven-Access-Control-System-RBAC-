package com.zoho.rbac_access_control.controllers;

import com.zoho.rbac_access_control.dto.MePermissionsResponse;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.CurrentUserService;
import com.zoho.rbac_access_control.services.PermissionQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class MeController {
    private final CurrentUserService currentUserService;
    private final PermissionQueryService permissionQueryService;

    public MeController(CurrentUserService currentUserService, PermissionQueryService permissionQueryService){
        this.currentUserService = currentUserService;
        this.permissionQueryService = permissionQueryService;
    }

    @GetMapping("/permissions")
    public MePermissionsResponse getMyPermission(@RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);
        return permissionQueryService.getPermissionsForUser(user);
    }
}
