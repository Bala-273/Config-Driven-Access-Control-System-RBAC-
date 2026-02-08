package com.zoho.rbac_access_control.controllers;

import com.zoho.rbac_access_control.dto.EmployeeResponse;
import com.zoho.rbac_access_control.entities.Employee;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final UserService userService;
    private final AccessControlService accessControlService;
    private final PermissionFilterService permissionFilterService;
    private final EntityUpdateService entityUpdateService;

    public EmployeeController(EmployeeService employeeService, UserService userService, AccessControlService accessControlService, PermissionFilterService permissionFilterService, EntityUpdateService entityUpdateService){
        this.employeeService = employeeService;
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.permissionFilterService = permissionFilterService;
        this.entityUpdateService = entityUpdateService;
    }

    private User getLoggedInUser(String usernameHeader){
        if(usernameHeader == null || usernameHeader.isEmpty()){
            throw new RuntimeException("X_USER header is required");
        }
        return userService.getByUsername(usernameHeader);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee,
                                   @RequestHeader(value = "X-USER", required = false) String username){

        User user = getLoggedInUser(username);
        if(!accessControlService.canEditTable(user, "employees")){
            throw new RuntimeException("Access Denied");
        }
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<Map<String, Object>> getAllEmployees(@RequestHeader(value = "X-USER", required = false) String username){
        User user = getLoggedInUser(username);
        if(!accessControlService.canViewTable(user, "employees")){
            throw new RuntimeException("Access denied: cannot view employees");
        }

        List<Employee> employees = employeeService.getAllEmployees();
        return permissionFilterService.filterList(employees, user, "employees");
    }

    @GetMapping("/{id}")
    public Map<String, Object> getEmployeeById(@PathVariable Integer id, @RequestHeader(value = "X-USER", required = false) String username){
        User user = getLoggedInUser(username);

        if(!accessControlService.canViewTable(user, "employees")){
            throw new RuntimeException("Access denied: cannot view employees");
        }

        Employee employee = employeeService.getEmployeeById(id);
        return permissionFilterService.filterObject(employee, user, "employees");
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee requestEmployee, @RequestHeader(value = "X-USER", required = false) String username){
        User user = getLoggedInUser(username);

        if(!accessControlService.canEditTable(user, "employees")){
            throw new RuntimeException("Access denied: cannot edit employees");
        }
        Employee existingEmployee = employeeService.getEmployeeById(id);

        Employee updatedEmployee = entityUpdateService.applyAllowedUpdates(
                existingEmployee, requestEmployee, user, "employees"
        );
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id, @RequestHeader(value = "X-USER", required = false) String username){
        User user = getLoggedInUser(username);
        if(!accessControlService.canEditTable(user, "employees")){
            throw new RuntimeException("Access denied: cannot delete employees");
        }
        employeeService.deleteEmployee(id);
    }
}
