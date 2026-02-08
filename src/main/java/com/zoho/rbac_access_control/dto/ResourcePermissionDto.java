package com.zoho.rbac_access_control.dto;

import java.util.HashMap;
import java.util.Map;

public class ResourcePermissionDto {

    private TablePermissionDto table;
    private Map<String, FieldPermissionDto> fields = new HashMap<>();

    public ResourcePermissionDto() {}

    public ResourcePermissionDto(TablePermissionDto table) {
        this.table = table;
    }

    public TablePermissionDto getTable() {
        return table;
    }

    public void setTable(TablePermissionDto table) {
        this.table = table;
    }

    public Map<String, FieldPermissionDto> getFields() {
        return fields;
    }

    public void setFields(Map<String, FieldPermissionDto> fields) {
        this.fields = fields;
    }
}
