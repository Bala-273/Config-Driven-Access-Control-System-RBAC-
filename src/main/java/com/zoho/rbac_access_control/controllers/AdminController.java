package com.zoho.rbac_access_control.controllers;

import com.zoho.rbac_access_control.dto.AssignRoleRequest;
import com.zoho.rbac_access_control.dto.CreatePermissionRequest;
import com.zoho.rbac_access_control.entities.Permission;
import com.zoho.rbac_access_control.entities.Role;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.PermissionService;
import com.zoho.rbac_access_control.services.RoleService;
import com.zoho.rbac_access_control.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final UserService userService;

    public  AdminController(RoleService roleService, PermissionService permissionService, UserService userService){
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.userService = userService;
    }

    private void verifyAdmin(String isAdminHeader){
        if(isAdminHeader == null || !isAdminHeader.equalsIgnoreCase("true")){
            throw new RuntimeException(("Only Admin can access this API"));
        }
    }

    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@RequestParam String roleName, @RequestHeader(value = "X-ADMIN", required = false) String isAdminHeader){
        verifyAdmin(isAdminHeader);
        return roleService.createRole(roleName);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(@RequestHeader(value = "X-ADMIN", required = false) String isAdminHeader){
        verifyAdmin(isAdminHeader);
        return roleService.getAllRoles();
    }

    @PostMapping("/permissions")
    @ResponseStatus(HttpStatus.CREATED)
    public Permission createPermission(@RequestBody CreatePermissionRequest request, @RequestHeader(value = "X-ADMIN", required = false) String isAdminHeader){
        verifyAdmin(isAdminHeader);

        return permissionService.createPermission(
                request.getRoleId(),
                request.getResource(),
                request.getAction(),
                request.getScope(),
                request.getFieldName()
        );
    }

    @GetMapping("/permissions")
    public List<Permission> getAllPermissions(@RequestHeader(value = "X-ADMIN", required = false) String isAdminHeader){
        verifyAdmin(isAdminHeader);
        return permissionService.getAllPermissions();
    }

    @PostMapping("/assign-role")
    public User assignRole(@RequestBody AssignRoleRequest request, @RequestHeader(value = "X-ADMIN", required = false) String isAdminHeader){
        verifyAdmin(isAdminHeader);
        return userService.assignRole(request.getUserId(), request.getRoleId());
    }
}
