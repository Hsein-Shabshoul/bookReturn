package com.example.employeeProject.department;
import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.department.Department;
import com.example.employeeProject.department.DepartmentRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/departments")
    public List<Department> getAllDepartments(){
        log.info("Requested All Department names");
        return departmentRepository.findAll();
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No Department was found with ID: "+id));
                log.info("Requested Department with ID="+id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(department);
    }

    @GetMapping("/departments/name/{name}")
    public ResponseEntity<List<Department>> getDepartmentByTitleContaining(@PathVariable String name){
        List<Department> department = departmentRepository.findByNameContaining(name);
        log.info("Departments containing " + name + "\n" + department);
        return ResponseEntity.ok(department);
    }

    @PostMapping("/departments")
    public Department createDepartment(@Valid @RequestBody Department department){
        Department newDepartment = departmentRepository.save(department);
        log.info("New Department Added:\n{}", newDepartment);
        return newDepartment;
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No Department was found with ID: "+id));
        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Updated Department with id={}\n{}",id,updatedDepartment);
        return ResponseEntity.ok(updatedDepartment);
    }
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDepartment(@PathVariable Long id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Department was found to delete with ID: " + id));

        departmentRepository.delete(department);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Deleted Department with id={}\nDeleted details were: {}",id, department);
        return ResponseEntity.ok(response);
    }



}
