package com.zoho.rbac_access_control.dto;

public class AssignRoleRequest {
    private String username;
    private Integer roleId;

    public AssignRoleRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
