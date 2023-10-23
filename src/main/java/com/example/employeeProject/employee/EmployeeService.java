package com.example.employeeProject.employee;

import com.example.employeeProject.department.DepartmentRepository;
import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.jobTitle.JobTitleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;
import java.util.stream.Collectors;
@Service
@Log4j2
@RequiredArgsConstructor
@CacheConfig(cacheNames = "employeeCache")
public class EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private JobTitleRepository jobTitleRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees(){
        log.info("Requested all Employees");
        return employeeRepository.findAll();
    }
    @Cacheable(key="#id")
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee was found with ID: " + id));
        log.info("Requested Employee with id=" + id + ".\n" + employee);
        return employee;
    }
    public Employee retrieveEmployeebyId(long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("No Employee was found with ID: "+ id);
        }
        Employee foundEmployee = employee.get();
        log.info("Requested Employee with id=" + id + ".\n" + foundEmployee);
        return foundEmployee;
    }
    @Cacheable(key="#name")
    public List<Employee> getEmployeeByName(String name) {
        List<Employee> employee = employeeRepository.findByFirstNameContaining(name);
        log.info("Employees with matching firstName= " + name + "\n" + employee);
        return employee;
    }
    public List<Employee> getAllEmployeesByJobTitleId(Long job_title_id){
        if (!jobTitleRepository.existsById(job_title_id)){
            throw new ResourceNotFoundException("No Job Titles was found with ID=" + job_title_id);
        }
        List<Employee> employees = employeeRepository.findByJobTitleId(job_title_id);
        return employees;
    }
    public List<Employee> getAllEmployeesByDepartmentId(Long department_id){
        List<Employee> employee = employeeRepository.findByJobTitle_Department_id(department_id);
        List<String> names = new ArrayList<>();
        names = employee.stream().map(Employee::getFirstName).collect(Collectors.toList());
        log.info(names);
        return employee;
    }
    public Employee createEmployeeWithJob(Long job_title_id, Employee employeeDetails){
        Optional<Employee> employee = jobTitleRepository.findById(job_title_id).map(jobTitle -> {
            employeeDetails.setJobTitle(jobTitle);
            return employeeRepository.save(employeeDetails);
        });
        if(employee.isEmpty()){
            throw new ResourceNotFoundException("No Job Title was found with ID: "+ job_title_id);
        }
        Employee newEmployee = employee.get();
        log.info("New Employee Added:\n" + newEmployee);
        return newEmployee;
    }
    @CachePut(key="#id")
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee was found to edit with ID: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Updated Employee with id={}\n{}", id, updatedEmployee);
        return updatedEmployee;
    }
    @CacheEvict(key = "#id")
    public Map<String, Boolean> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Employee was found to delete with ID: " + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Deleted Employee with id={}\nDeleted details were: {}",id, employee);
        return response;
    }
}
