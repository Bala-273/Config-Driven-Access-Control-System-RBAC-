package com.zoho.rbac_access_control.services;

import com.zoho.rbac_access_control.entities.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Integer id);
    Employee updateEmployee(Integer id, Employee updatedEmployee);
    void deleteEmployee(Integer id);
}
