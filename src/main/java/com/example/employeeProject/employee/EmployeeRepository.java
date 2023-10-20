package com.example.employeeProject.employee;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "employeeCache")
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    List <Employee> findByFirstNameContaining(String name);
    List <Employee> findByJobTitleId(Long id);
    List<Employee> findByJobTitle_Department_id(Long id);
}
