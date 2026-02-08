package com.zoho.rbac_access_control.dto;

public class AssignRoleRequest {
    private Integer userId;
    private Integer roleId;

    public AssignRoleRequest(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
