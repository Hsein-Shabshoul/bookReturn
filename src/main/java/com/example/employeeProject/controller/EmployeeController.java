package com.example.employeeProject.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.model.JobTitle;
import com.example.employeeProject.repository.DepartmentRepository;
import com.example.employeeProject.repository.JobTitleRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.model.Employee;
import com.example.employeeProject.repository.EmployeeRepository;
@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")

public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        log.info("Requested all Employees.");
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/job_titles/{job_title_id}")
    public ResponseEntity<List<Employee>> getAllEmployeesByJobTitleId(@PathVariable Long job_title_id){
        if (!jobTitleRepository.existsById(job_title_id)){
            throw new ResourceNotFoundException("No Job Titles was found with ID=" + job_title_id);
        }
        List<Employee> employees = employeeRepository.findByJobTitleId(job_title_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employees);
    }

//    @GetMapping("/employees/departments/{department_id}")
//    public ResponseEntity<List<JobTitle>> getAllEmployeesByDepartmentId(@PathVariable Long department_id){
//        if(!departmentRepository.existsById(department_id)){
//            throw new EmployeeNotFoundException("No Department was found with ID="+department_id);
//        }
//        List<JobTitle> jobTitles = jobTitleRepository.findByDepartmentId(department_id);
//
////////////////PLEASE HELP HERE
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(jobTitles);
//
//    }

    @GetMapping("/employees/departments/{department_id}")
    public ResponseEntity<List<Employee>> getAllEmployeesByDepartmentId(@PathVariable Long department_id){

        List<Employee> employee = employeeRepository.findByJobTitle_Department_id(department_id);
        //log.info("Employees belonging to Department id="+department_id+ " requested:\n" + employee);
        //List<String > names = employee.stream().forEach(employee1 -> employee1.getFirstName());
        //List<Employee> newEmp = employee.stream().toList();
        //employee.forEach(employee1 -> employee1.getFirstName());
        List<String> names = new ArrayList<>();
        names = employee.stream().map(Employee::getFirstName).collect(Collectors.toList());
//      for(Employee e : employee){
//          names.add(e.getFirstName());
//      }
        log.info(names);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employee);

    }

    // create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        Employee newEmployee = employeeRepository.save(employee);
        log.info("New Employee Added:\n" + newEmployee);
        return newEmployee; //this returns a new Employee(with Id generated)
    }

    @PostMapping("/employees/job_titles/{job_title_id}")
    public ResponseEntity<Employee> createEmployeeWithJob(@PathVariable Long job_title_id,
                                                          @RequestBody Employee employeeDetails){

        Optional<Employee> employee = jobTitleRepository.findById(job_title_id).map(jobTitle -> {
            employeeDetails.setJobTitle(jobTitle);
            return employeeRepository.save(employeeDetails);
        });
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("No Job Title was found with ID: "+ job_title_id);
        }
        Employee newEmployee = employee.get();
        log.info("New Employee Added:\n" + newEmployee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newEmployee);
    }

    // get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {//?@PathVariable("id") Long id

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee was found with ID: " + id));
        log.info("Requested Employee with id=" + id + ".\n" + employee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employee);
    }

    @GetMapping("/employees/e/{id}")
    public ResponseEntity<Employee> retrieveEmployeebyId(@PathVariable long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("No Employee was found with ID: "+ id);
        }
        Employee foundEmployee = employee.get();
        log.info("Requested Employee with id=" + id + ".\n" + foundEmployee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundEmployee);
    }

    // get employee by firstname rest api

    @GetMapping("/employees/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable String name) {
        List<Employee> employee = employeeRepository.findByFirstNameLike(name);
        log.info("Employees with matching firstName= " + name + "\n" + employee);
        return ResponseEntity.ok(employee);
    }

//    @GetMapping("/employees/name")
//    public ResponseEntity<List<Employee>> getEmployeeByName(@RequestParam String name) {
//        return new ResponseEntity<List<Employee>>(employeeRepository.findByFirstName(name), HttpStatus.OK);
//    }

    // update employee rest api

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee was found to edit with ID: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Updated Employee with id={}\n{}", id, updatedEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee was found to delete with ID: " + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Deleted Employee with id={}\nDeleted details were: {}",id, employee);
        return ResponseEntity.ok(response);
    }
}
