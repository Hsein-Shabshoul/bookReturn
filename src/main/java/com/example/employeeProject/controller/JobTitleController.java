package com.example.employeeProject.controller;


import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.model.Employee;
import com.example.employeeProject.model.JobTitle;
import com.example.employeeProject.repository.DepartmentRepository;
import com.example.employeeProject.repository.JobTitleRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class JobTitleController {

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/job_titles")
    public List<JobTitle> getAllJobTitles(){
        log.info("Requested All job titles.");
        return jobTitleRepository.findAll();
    }
    @PostMapping("/job_titles")
    public JobTitle createJobTitle(@Valid @RequestBody JobTitle jobTitle){
        JobTitle newJobTitle = jobTitleRepository.save(jobTitle);
        log.info("New Job Title Added:\n{}", newJobTitle);
        return newJobTitle;
    }
    @PostMapping("/job_titles/departments/{department_id}")
    public ResponseEntity<JobTitle> createJobTitleWithDepartment(@PathVariable Long department_id,
                                                                 @RequestBody JobTitle jobTitleDetails){
        JobTitle jobTitle = departmentRepository.findById(department_id).map(department -> {
            jobTitleDetails.setDepartment(department);
            return jobTitleRepository.save(jobTitleDetails);
        }).orElseThrow(() -> new EmployeeNotFoundException("No Department was found with ID: " + department_id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobTitle);
    }
    @GetMapping("/job_titles/departments/{department_id}")
    public ResponseEntity<List<JobTitle>> getAllJobTitlesByDepartmentId(@PathVariable Long department_id){
        if(!departmentRepository.existsById(department_id)){
            throw new EmployeeNotFoundException("No Department was found with ID="+department_id);
        }
        List<JobTitle> jobTitles = jobTitleRepository.findByDepartmentId(department_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobTitles);
    }

    @GetMapping("/job_titles/{id}")
    public ResponseEntity<JobTitle> getJobTitleById(@PathVariable Long id) {

        JobTitle jobTitle= jobTitleRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No JobTitle was found with ID: " + id));
        log.info("Requested JobTitle with id=" + id + ".\n" + jobTitle);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobTitle);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/job_titles/title/{title}")
    public ResponseEntity<List<JobTitle>> getJobByTitleContaining(@PathVariable String title) {
        List<JobTitle> jobTitle = jobTitleRepository.findByTitleContaining(title);
        log.info("Job Titles containing " + title + "\n" + jobTitle);
        return ResponseEntity.ok(jobTitle);
    }

    @PutMapping("/job_titles/{id}")
    public ResponseEntity<JobTitle> updateJobTitle(@PathVariable Long id, @RequestBody JobTitle jobTitleDetails){
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No Employee was found to edit with ID: " + id));
        jobTitle.setTitle(jobTitleDetails.getTitle());
        jobTitle.setDescription(jobTitleDetails.getDescription());

        JobTitle updatedJob = jobTitleRepository.save(jobTitle);
        log.info("Updated Job title with id={}\n{}", id, updatedJob);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/job_titles/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteJobTitle(@PathVariable Long id){
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Title was found to delete with ID: " + id));

        jobTitleRepository.delete(jobTitle);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Deleted Job Title with id={}\nDeleted details were: {}",id, jobTitle);
        return ResponseEntity.ok(response);
    }


}
