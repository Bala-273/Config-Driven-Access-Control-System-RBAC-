package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.User;

public interface AccessControlService {
    boolean canViewTable(User user, String resource);
    boolean canEditTable(User user, String resource);
    boolean canViewField(User user, String resource, String fieldName);
    boolean canEditField(User user, String resource, String fieldName);
}
