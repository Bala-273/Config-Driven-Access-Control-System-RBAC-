package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.User;

import java.util.List;
import java.util.Map;

public interface PermissionFilterService {
    Map<String, Object> filterObject(Object entity, User user, String resource);
    List<Map<String, Object>> filterList(List<?> entities, User user, String resource);
}
