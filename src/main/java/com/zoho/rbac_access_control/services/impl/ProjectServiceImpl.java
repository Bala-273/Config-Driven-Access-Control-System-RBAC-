package com.zoho.rbac_access_control.services.impl;

import com.zoho.rbac_access_control.entities.Project;
import com.zoho.rbac_access_control.repositories.ProjectRepository;
import com.zoho.rbac_access_control.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public Project updateProject(Integer id, Project updatedProject) {
        Project existingProject = getProjectById(id);

        existingProject.setName(updatedProject.getName());
        existingProject.setClientName(updatedProject.getClientName());
        existingProject.setBudget(updatedProject.getBudget());
        existingProject.setStartDate(updatedProject.getStartDate());
        existingProject.setEndDate(updatedProject.getEndDate());
        existingProject.setStatus(updatedProject.getStatus());

        return projectRepository.save(existingProject);
    }

    @Override
    public void deleteProject(Integer id) {
        Project existingProject = getProjectById(id);
        projectRepository.delete(existingProject);
        System.out.println("Project deleted");
    }
}
