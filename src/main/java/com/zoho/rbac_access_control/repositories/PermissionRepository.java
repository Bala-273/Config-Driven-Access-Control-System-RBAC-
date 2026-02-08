package com.zoho.rbac_access_control.repositories;

import com.zoho.rbac_access_control.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findByRoleIdIn(List<Integer> roleIds);
}
