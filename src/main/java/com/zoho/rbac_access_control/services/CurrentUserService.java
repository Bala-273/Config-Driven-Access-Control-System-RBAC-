package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.User;

public interface CurrentUserService {
    User getLoggedInUser(String usernameHeader);
}
