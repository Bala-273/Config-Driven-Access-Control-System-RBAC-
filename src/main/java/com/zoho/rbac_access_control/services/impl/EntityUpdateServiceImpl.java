package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.AccessControlService;
import com.zoho.rbac_access_control.services.EntityUpdateService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class EntityUpdateServiceImpl implements EntityUpdateService {
    private final AccessControlService accessControlService;

    public EntityUpdateServiceImpl(AccessControlServiceImpl accessControlService){
        this.accessControlService = accessControlService;
    }

    public <T> T applyAllowedUpdates(T existingEntity, T requestEntity, User user, String resource){
        if(existingEntity == null || requestEntity == null){
            throw new RuntimeException("Entity cannot be null");
        }

        Field[] fields = requestEntity.getClass().getDeclaredFields();
        boolean hasAnyAllowedField = false;

        for (Field field : fields) {

            field.setAccessible(true);
            String fieldName = field.getName();

            // never allow updating ID
            if (fieldName.equalsIgnoreCase("id")) {
                continue;
            }

            try {
                Object newValue = field.get(requestEntity);

                if (newValue == null) {
                    continue;
                }

                boolean canEdit = accessControlService.canEditField(user, resource, fieldName);

                if (!canEdit) {
                    throw new RuntimeException("Access denied: cannot edit field " + fieldName + "in" + resource);
                }

                field.set(existingEntity, newValue);
                hasAnyAllowedField = true;

            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error updating field: " + fieldName);
            }
        }

        return existingEntity;
    }
}
