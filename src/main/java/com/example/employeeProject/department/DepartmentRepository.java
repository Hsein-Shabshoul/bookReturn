package com.example.employeeProject.department;

import com.example.employeeProject.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
    List<Department> findByNameContaining(String name);
}
