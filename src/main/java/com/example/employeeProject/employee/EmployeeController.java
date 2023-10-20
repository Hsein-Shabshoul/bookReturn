package com.example.employeeProject.employee;

import java.util.*;
import java.util.stream.Collectors;

import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.department.DepartmentRepository;
import com.example.employeeProject.jobTitle.JobTitleRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.employee.Employee;
import com.example.employeeProject.employee.EmployeeRepository;
@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    public EmployeeController() {
    }
    // get all employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    @GetMapping("/employees/job_titles/{job_title_id}")
    public ResponseEntity<List<Employee>> getAllEmployeesByJobTitleId(@PathVariable Long job_title_id){
        return ResponseEntity.ok(employeeService.getAllEmployeesByJobTitleId(job_title_id));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/employees/departments/{department_id}")
    public ResponseEntity<List<Employee>> getAllEmployeesByDepartmentId(@PathVariable Long department_id){
        return ResponseEntity.ok(employeeService.getAllEmployeesByDepartmentId(department_id));
    }
    @PostMapping("/employees/job_titles/{job_title_id}")
    public ResponseEntity<Employee> createEmployeeWithJob(@PathVariable Long job_title_id,
                                                          @RequestBody Employee employeeDetails){
        return ResponseEntity.ok(employeeService.createEmployeeWithJob(job_title_id,employeeDetails));
    }
    // get employee by id rest api
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
    @GetMapping("/employees/e/{id}")
    public ResponseEntity<Employee> retrieveEmployeebyId(@PathVariable long id){
        return ResponseEntity.ok(employeeService.retrieveEmployeebyId(id));
    }
    @GetMapping("/employees/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.getEmployeeByName(name));
    }
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        return ResponseEntity.ok(employeeService.updateEmployee(id,employeeDetails));
    }
    // delete employee rest api
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
