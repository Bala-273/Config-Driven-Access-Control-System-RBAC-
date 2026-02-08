package com.zoho.rbac_access_control.controllers;

import com.zoho.rbac_access_control.entities.Project;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final AccessControlService accessControlService;
    private final PermissionFilterService permissionFilterService;
    private final EntityUpdateService entityUpdateService;

    public ProjectController(ProjectService projectService, UserService userService, AccessControlService accessControlService, PermissionFilterService permissionFilterService, EntityUpdateService entityUpdateService){
        this.projectService = projectService;
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.permissionFilterService = permissionFilterService;
        this.entityUpdateService = entityUpdateService;
    }

    private User getLoggedInUser(String usernameHeader){
        if(usernameHeader == null || usernameHeader.isEmpty()){
            throw new RuntimeException("X-USER header is required");
        }
        return userService.getByUsername(usernameHeader);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }

    @GetMapping
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Integer id){
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Integer id, @RequestBody Project project){
        return projectService.updateProject(id, project);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Integer id){
        projectService.deleteProject(id);
    }

}
