package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project);
    List<Project> getAllProjects();
    Project getProjectById(Integer id);
    Project updateProject(Integer id, Project updatedProject);
    void deleteProject(Integer id);
}
