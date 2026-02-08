package com.zoho.rbac_access_control.repositories;

import com.zoho.rbac_access_control.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
