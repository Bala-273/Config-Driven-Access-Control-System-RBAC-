package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.Role;
import com.zoho.rbac_access_control.repositories.RoleRepository;
import com.zoho.rbac_access_control.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role createRole(String roleName){
        roleRepository.findByRoleName(roleName).ifPresent(
                r -> {
                    throw new RuntimeException("Role already exists: " + roleName);
                }
        );

        Role role = new Role();
        role.setRoleName(roleName);
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role getRoleById(Integer id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
