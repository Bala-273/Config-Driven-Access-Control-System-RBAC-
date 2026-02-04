package com.zoho.rbac_access_control;

import com.zoho.rbac_access_control.entities.Employee;
import com.zoho.rbac_access_control.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class RbacAccessControlApplication {

	public static void main(String[] args) {

		SpringApplication.run(RbacAccessControlApplication.class, args);
	}

	@Bean
	public CommandLineRunner testDatabase(EmployeeRepository repository){
		return args -> {
			Employee emp = new Employee();
			emp.setName("John Doe");
			emp.setEmail("john.doe@zoho.com");
			emp.setDepartment("Engineering");
			emp.setSalary(75000);
			emp.setJoiningDate(LocalDate.now());

			System.out.println("---Saving Employee---");
			repository.save(emp);

			System.out.println("---Fetching Employee Data---");
			repository.findAll().forEach(employee -> {
				System.out.println("ID: " + employee.getId());
				System.out.println("Name: " + employee.getName());
				System.out.println("Email: " + employee.getEmail());
			});
		};
	}

}
