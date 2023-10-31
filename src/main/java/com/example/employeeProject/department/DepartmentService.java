package com.example.employeeProject.department;

import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.rabbitMq.RabbitMQConfig;
import com.example.employeeProject.rabbitMq.RabbitMQConfig.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Log4j2
@RequiredArgsConstructor
@CacheConfig(cacheNames = "departmentCache")
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<Department> getAllDepartments(){
        log.info("Requested All Department names");
        return departmentRepository.findAll();
    }
    public Department getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No Department was found with ID: "+id));
        log.info("Requested Department with ID="+id);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DEPARTMENT_DIRECT,RabbitMQConfig.ROUTING_KEY_DEPARTMENT_FIND,department);
        return department;
    }
    @Cacheable(key = "#name")
    public List<Department> getDepartmentByTitleContaining(String name){
        List<Department> department = departmentRepository.findByNameContaining(name);
        log.info("Departments containing " + name + "\n" + department);
        return department;
    }
    public Department createDepartment(Department department){
        Department newDepartment = departmentRepository.save(department);
        log.info("New Department Added:\n{}", newDepartment);
        return newDepartment;
    }
    public Department updateDepartment(Long id, Department departmentDetails){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No Department was found with ID: "+id));
        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Updated Department with id={}\n{}",id,updatedDepartment);
        return updatedDepartment;
    }
    @CacheEvict()
    public Map<String, Boolean> deleteDepartment(Long id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Department was found to delete with ID: " + id));
        departmentRepository.delete(department);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Deleted Department with id={}\nDeleted details were: {}",id, department);
        return response;
    }
}
