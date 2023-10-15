package com.example.employeeProject.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.employeeProject.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    List <Employee> findByFirstNameLike(String name);
    //findBy should be followed by the exact name of the entity parameter
    List <Employee> findByJobTitleId(Long id);

    List<Employee> findByJobTitle_Department_id(Long id);
}
