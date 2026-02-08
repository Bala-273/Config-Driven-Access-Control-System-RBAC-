package com.zoho.rbac_access_control.controllers;

import com.zoho.rbac_access_control.entities.Project;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final AccessControlService accessControlService;
    private final PermissionFilterService permissionFilterService;
    private final EntityUpdateService entityUpdateService;
    private final CurrentUserService currentUserService;

    public ProjectController(ProjectService projectService, UserService userService, AccessControlService accessControlService, PermissionFilterService permissionFilterService, EntityUpdateService entityUpdateService, CurrentUserService currentUserService){
        this.projectService = projectService;
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.permissionFilterService = permissionFilterService;
        this.entityUpdateService = entityUpdateService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody Project project,
                                 @RequestHeader(value = "X-USER", required = false) String username){

        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canEditTable(user, "projects")){
            throw new RuntimeException("Access denied");
        }
        return projectService.createProject(project);
    }

    @GetMapping
    public List<Map<String, Object>> getAllProjects(@RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canViewTable(user, "projects")){
            throw new RuntimeException("Access denied: cannot view projects");
        }

        List<Project> projects = projectService.getAllProjects();
        return permissionFilterService.filterList(projects, user, "projects");
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProjectById(@PathVariable Integer id, @RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canViewTable(user, "projects")){
            throw new RuntimeException("Access denied: cannot view projects");
        }

        Project project = projectService.getProjectById(id);
        return permissionFilterService.filterObject(project, user, "projects");
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Integer id, @RequestBody Project requestProject, @RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canEditTable(user, "projects")){
            throw new RuntimeException("Access denied: cannot edit projects");
        }

        Project existingProject = projectService.getProjectById(id);
        Project updatedProject = entityUpdateService.applyAllowedUpdates(existingProject, requestProject, user, "projects");
        return projectService.updateProject(id, updatedProject);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Integer id, @RequestHeader(value = "X-USER", required = false) String username){
        User user = currentUserService.getLoggedInUser(username);

        if(!accessControlService.canEditTable(user, "projects")){
            throw new RuntimeException("Access denied: cannot delete projects");
        }
        projectService.deleteProject(id);
    }

}
