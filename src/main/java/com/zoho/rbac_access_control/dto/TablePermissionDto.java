package com.zoho.rbac_access_control.dto;

public class TablePermissionDto {

    private boolean view;
    private boolean edit;

    public TablePermissionDto() {}

    public TablePermissionDto(boolean view, boolean edit) {
        this.view = view;
        this.edit = edit;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
