package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.Role;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.exceptions.ResourceNotFoundException;
import com.zoho.rbac_access_control.repositories.RoleRepository;
import com.zoho.rbac_access_control.repositories.UserRepository;
import com.zoho.rbac_access_control.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User createUser(String username, String password) {
        userRepository.findByUsername(username).ifPresent(
                u -> {throw new RuntimeException("Username already exists");}
        );

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User assignRole(String username, Integer roleId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
