package com.zoho.rbac_access_control.dto;

import com.zoho.rbac_access_control.enums.ActionType;
import com.zoho.rbac_access_control.enums.ScopeType;

public class CreatePermissionRequest {
    private Integer roleId;
    private String resource;
    private ActionType action;
    private ScopeType scope;
    private String fieldName;

    public CreatePermissionRequest(){}

    public Integer getRoleId(){ return roleId; }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public ScopeType getScope() {
        return scope;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
