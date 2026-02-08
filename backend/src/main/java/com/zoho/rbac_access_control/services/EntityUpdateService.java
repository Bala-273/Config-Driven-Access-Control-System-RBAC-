package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.User;

public interface EntityUpdateService {

    <T> T applyAllowedUpdates(T existingEntity,
                              T requestEntity,
                              User user,
                              String resource);
}
