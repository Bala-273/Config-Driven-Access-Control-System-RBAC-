package com.zoho.rbac_access_control.repositories;

import com.zoho.rbac_access_control.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
