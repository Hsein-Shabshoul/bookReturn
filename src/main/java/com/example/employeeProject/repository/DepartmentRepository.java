package com.example.employeeProject.repository;

import com.example.employeeProject.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
    List<Department> findByNameContaining(String name);
}
