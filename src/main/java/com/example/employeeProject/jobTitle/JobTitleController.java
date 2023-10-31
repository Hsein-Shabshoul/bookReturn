package com.example.employeeProject.jobTitle;
import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.jobTitle.JobTitle;
import com.example.employeeProject.department.DepartmentRepository;
import com.example.employeeProject.jobTitle.JobTitleRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Log4j2
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class JobTitleController {
    @Autowired
    private JobTitleService jobTitleService;
    @Autowired
    private JobTitleRepository jobTitleRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @GetMapping("/job_titles")
    public ResponseEntity<List<JobTitle>> getAllJobTitles(){
        return ResponseEntity.ok(jobTitleService.getAllJobTitles());
    }
    @PostMapping("/job_titles")
    public ResponseEntity<JobTitle> createJobTitle(@Valid @RequestBody JobTitle jobTitle){
        return ResponseEntity.ok(jobTitleService.createJobTitle(jobTitle));
    }
    @PostMapping("/job_titles/departments/{department_id}")
    public ResponseEntity<JobTitle> createJobTitleWithDepartment(@PathVariable Long department_id,
                                                                 @RequestBody JobTitle jobTitleDetails){
        return ResponseEntity.ok(jobTitleService.createJobTitleWithDepartment(department_id,jobTitleDetails));
    }
    @GetMapping("/job_titles/departments/{department_id}")
    public ResponseEntity<List<JobTitle>> getAllJobTitlesByDepartmentId(@PathVariable Long department_id){
        return ResponseEntity.ok(jobTitleService.getAllJobTitlesByDepartmentId(department_id));
    }

    @GetMapping("/job_titles/{id}")
    public ResponseEntity<JobTitle> getJobTitleById(@PathVariable Long id) {
        return ResponseEntity.ok(jobTitleService.getJobTitleById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/job_titles/title/{title}")
    public ResponseEntity<List<JobTitle>> getJobByTitleContaining(@PathVariable String title) {
        return ResponseEntity.ok(jobTitleService.getJobByTitleContaining(title));
    }

    @PutMapping("/job_titles/{id}")
    public ResponseEntity<JobTitle> updateJobTitle(@PathVariable Long id, @RequestBody JobTitle jobTitleDetails){
        return ResponseEntity.ok(jobTitleService.updateJobTitle(id,jobTitleDetails));
    }

    @DeleteMapping("/job_titles/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteJobTitle(@PathVariable Long id){
        return ResponseEntity.ok(jobTitleService.deleteJobTitle(id));
    }
}
