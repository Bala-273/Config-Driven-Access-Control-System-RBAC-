package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.repositories.UserRepository;
import com.zoho.rbac_access_control.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username, String password) {
        userRepository.findByUsername(username).ifPresent(
                u -> {throw new RuntimeException("Username already exists");}
        );

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if(!user.getPassword().equals(password)) throw new RuntimeException("Invalid password");
        return user;
    }
}
