package com.example.employeeProject.department;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

//    @GetMapping("/departments")
//    public ResponseEntity<List<Department>> getAllDepartments(){
//        return ResponseEntity.ok(departmentService.getAllDepartments());
//    }
@GetMapping("/departments")
public List<Department> getAllDepartments(){
    return departmentService.getAllDepartments();
}
    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }
    @GetMapping("/departments/name/{name}")
    public ResponseEntity<List<Department>> getDepartmentByTitleContaining(@PathVariable String name){
        return ResponseEntity.ok(departmentService.getDepartmentByTitleContaining(name));
    }
    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department){
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }
    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails){
        return ResponseEntity.ok(departmentService.updateDepartment(id,departmentDetails));
    }
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDepartment(@PathVariable Long id){
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }
}
