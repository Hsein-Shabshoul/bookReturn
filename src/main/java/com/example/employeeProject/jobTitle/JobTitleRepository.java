package com.example.employeeProject.jobTitle;


import com.example.employeeProject.jobTitle.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    List<JobTitle> findByTitleContaining(String title);
    List<JobTitle> findByDepartmentId(Long id);
}
