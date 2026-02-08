package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.CurrentUserService;
import com.zoho.rbac_access_control.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    private final UserService userService;

    public CurrentUserServiceImpl(UserService userService){
        this.userService = userService;
    }
    @Override
    public User getLoggedInUser(String usernameHeader) {
        if(usernameHeader == null || usernameHeader.isEmpty()){
            throw new RuntimeException("X-USER header is required");
        }
        return userService.getByUsername(usernameHeader);
    }
}
