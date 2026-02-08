package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.AccessControlService;
import com.zoho.rbac_access_control.services.PermissionFilterService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionFilterServiceImpl implements PermissionFilterService {
    private final AccessControlService accessControlService;

    public PermissionFilterServiceImpl(AccessControlService accessControlService){
        this.accessControlService = accessControlService;
    }
    @Override
    public Map<String, Object> filterObject(Object entity, User user, String resource) {
        if(entity == null) return null;

        Map<String, Object> response = new LinkedHashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String fieldName = field.getName();

            if(fieldName.equals("id")){
                try{
                    response.put(fieldName, field.get(entity));
                } catch (IllegalAccessException e){
                    response.put(fieldName, null);
                }
                continue;
            }

            boolean canView = accessControlService.canViewField(user, resource, fieldName);

            if(canView){
                try{
                    response.put(fieldName, field.get(entity));
                }catch (IllegalAccessException e){
                    response.put(fieldName, null);
                }
            }
        }
        return response;
    }

    @Override
    public List<Map<String, Object>> filterList(List<?> entities, User user, String resource) {
        List<Map<String, Object>> result = new ArrayList<>();

        for(Object entity:entities){
            result.add(filterObject(entity, user, resource));
        }
        return result;
    }
}
