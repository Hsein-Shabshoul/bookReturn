package com.example.employeeProject.jobTitle;

import com.example.employeeProject.department.DepartmentRepository;
import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class JobTitleService {
    @Autowired
    private JobTitleRepository jobTitleRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<JobTitle> getAllJobTitles(){
        log.info("Requested All job titles.");
        return jobTitleRepository.findAll();
    }
    public JobTitle createJobTitle(JobTitle jobTitle){
        JobTitle newJobTitle = jobTitleRepository.save(jobTitle);
        log.info("New Job Title Added:\n{}", newJobTitle);
        return newJobTitle;
    }
    public JobTitle createJobTitleWithDepartment(Long department_id, JobTitle jobTitleDetails){
        JobTitle jobTitle = departmentRepository.findById(department_id).map(department -> {
            jobTitleDetails.setDepartment(department);
            return jobTitleRepository.save(jobTitleDetails);
        }).orElseThrow(() -> new EmployeeNotFoundException("No Department was found with ID: " + department_id));
        return jobTitle;
    }
    public List<JobTitle> getAllJobTitlesByDepartmentId(Long department_id){
        if(!departmentRepository.existsById(department_id)){
            throw new EmployeeNotFoundException("No Department was found with ID="+department_id);
        }
        List<JobTitle> jobTitles = jobTitleRepository.findByDepartmentId(department_id);
        return jobTitles;
    }
    public JobTitle getJobTitleById(Long id) {
        JobTitle jobTitle= jobTitleRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No JobTitle was found with ID: " + id));
        log.info("Requested JobTitle with id=" + id + ".\n" + jobTitle);
        return jobTitle;
    }
    public List<JobTitle> getJobByTitleContaining(String title) {
        List<JobTitle> jobTitle = jobTitleRepository.findByTitleContaining(title);
        log.info("Job Titles containing " + title + "\n" + jobTitle);
        return jobTitle;
    }
    public JobTitle updateJobTitle(Long id, JobTitle jobTitleDetails){
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No Employee was found to edit with ID: " + id));
        jobTitle.setTitle(jobTitleDetails.getTitle());
        jobTitle.setDescription(jobTitleDetails.getDescription());
        JobTitle updatedJob = jobTitleRepository.save(jobTitle);
        log.info("Updated Job title with id={}\n{}", id, updatedJob);
        return updatedJob;
    }
    public Map<String, Boolean> deleteJobTitle(Long id){
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Title was found to delete with ID: " + id));
        jobTitleRepository.delete(jobTitle);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Deleted Job Title with id={}\nDeleted details were: {}",id, jobTitle);
        return response;
    }
}