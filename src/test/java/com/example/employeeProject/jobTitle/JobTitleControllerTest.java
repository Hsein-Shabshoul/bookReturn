package com.example.employeeProject.jobTitle;

import static org.junit.jupiter.api.Assertions.*;

import com.example.employeeProject.jobTitle.JobTitle;
import com.example.employeeProject.jobTitle.JobTitleController;
import com.example.employeeProject.jobTitle.JobTitleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
class JobTitleControllerTest {
    @InjectMocks
    private JobTitleController jobTitleController;
    @Mock
    private JobTitleService jobTitleService;

    @Mock
    private JobTitleRepository jobTitleRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllJobTitles() {
        List<JobTitle> jobTitles = new ArrayList<>();
        jobTitles.add(new JobTitle("Title 1", "Description 1"));
        jobTitles.add(new JobTitle("Title 2", "Description 2"));

        when(jobTitleService.getAllJobTitles()).thenReturn(jobTitles);
        //when(jobTitleRepository.findAll()).thenReturn(jobTitles);
        ResponseEntity<List<JobTitle>> response = jobTitleController.getAllJobTitles();

        assertEquals(2, response.getBody().size());
        assertEquals("Title 1", response.getBody().get(0).getTitle());
        assertEquals("Description 1", response.getBody().get(0).getDescription());
        assertEquals("Title 2", response.getBody().get(1).getTitle());
        assertEquals("Description 2", response.getBody().get(1).getDescription());
    }

    @Test
    void createJobTitle() {
        JobTitle newJobTitle = new JobTitle("New Title", "New Description");
        when(jobTitleService.createJobTitle(Mockito.any(JobTitle.class))).thenReturn(newJobTitle);
        ResponseEntity<JobTitle> response = jobTitleController.createJobTitle(newJobTitle);
        assertEquals("New Title", response.getBody().getTitle());
        assertEquals("New Description", response.getBody().getDescription());
    }

    @Test
    void createJobTitleWithDepartment() {
        Long departmentId = 1L;
        JobTitle newJobTitle = new JobTitle("New Title", "New Description");
        when(jobTitleService.createJobTitleWithDepartment(departmentId, newJobTitle)).thenReturn(newJobTitle);
        ResponseEntity<JobTitle> response = jobTitleController.createJobTitleWithDepartment(departmentId, newJobTitle);
        assertEquals("New Title", response.getBody().getTitle());
        assertEquals("New Description", response.getBody().getDescription());
    }

    @Test
    void getAllJobTitlesByDepartmentId() {
        Long departmentId = 1L;
        List<JobTitle> jobTitles = new ArrayList<>();
        jobTitles.add(new JobTitle("Title 1", "Description 1"));
        jobTitles.add(new JobTitle("Title 2", "Description 2"));
        when(jobTitleService.getAllJobTitlesByDepartmentId(departmentId)).thenReturn(jobTitles);
        ResponseEntity<List<JobTitle>> response = jobTitleController.getAllJobTitlesByDepartmentId(departmentId);
        assertEquals(2, response.getBody().size());
        assertEquals("Title 1", response.getBody().get(0).getTitle());
        assertEquals("Description 1", response.getBody().get(0).getDescription());
        assertEquals("Title 2", response.getBody().get(1).getTitle());
        assertEquals("Description 2", response.getBody().get(1).getDescription());
    }

    @Test
    void getJobTitleById() {
        Long jobTitleId = 1L;
        JobTitle jobTitle = new JobTitle("Title 1", "Description 1");
        jobTitle.setId(jobTitleId);
        when(jobTitleService.getJobTitleById(jobTitleId)).thenReturn(jobTitle);
        ResponseEntity<JobTitle> response = jobTitleController.getJobTitleById(jobTitleId);
        assertEquals("Title 1", response.getBody().getTitle());
        assertEquals("Description 1", response.getBody().getDescription());
        assertEquals(jobTitleId, response.getBody().getId());
    }

    @Test
    void getJobByTitleContaining() {
        String title = "Title";

        List<JobTitle> jobTitles = new ArrayList<>();
        jobTitles.add(new JobTitle("Title 1", "Description 1"));
        jobTitles.add(new JobTitle("Title 2", "Description 2"));
        when(jobTitleService.getJobByTitleContaining(title)).thenReturn(jobTitles);
        ResponseEntity<List<JobTitle> > response = jobTitleController.getJobByTitleContaining(title);
        assertEquals(2, response.getBody().size());
        assertEquals("Title 1", response.getBody().get(0).getTitle());
        assertEquals("Description 1", response.getBody().get(0).getDescription());
        assertEquals("Title 2", response.getBody().get(1).getTitle());
        assertEquals("Description 2", response.getBody().get(1).getDescription());
    }

    @Test
    void updateJobTitle() {
        Long jobTitleId = 1L;
        JobTitle existingJobTitle = new JobTitle("Title 1", "Description 1");
        existingJobTitle.setId(jobTitleId);
        JobTitle updatedJobTitle = new JobTitle("Updated Title", "Updated Description");
        updatedJobTitle.setId(jobTitleId);
        when(jobTitleService.updateJobTitle(jobTitleId, updatedJobTitle)).thenReturn(updatedJobTitle);
        ResponseEntity<JobTitle> response = jobTitleController.updateJobTitle(jobTitleId, updatedJobTitle);
        assertEquals("Updated Title", response.getBody().getTitle());
        assertEquals("Updated Description", response.getBody().getDescription());
        assertEquals(jobTitleId, response.getBody().getId());
    }

    @Test
    void deleteJobTitle() {
        Long jobTitleId = 1L;
        when(jobTitleService.deleteJobTitle(jobTitleId)).thenReturn(Map.of("deleted", true));
        ResponseEntity<Map<String, Boolean>> response = jobTitleController.deleteJobTitle(jobTitleId);
        assertEquals(true, response.getBody().get("deleted"));
    }
}