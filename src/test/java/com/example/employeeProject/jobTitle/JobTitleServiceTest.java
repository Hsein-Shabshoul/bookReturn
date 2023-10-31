package com.example.employeeProject.jobTitle;

import com.example.employeeProject.department.Department;
import com.example.employeeProject.department.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class JobTitleServiceTest {
    @InjectMocks
    private JobTitleService jobTitleService;
    @Mock
    private JobTitleRepository jobTitleRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllJobTitles() {
        List<JobTitle> jobTitles = new ArrayList<>();
        jobTitles.add(new JobTitle("Title 1", "Description 1"));
        jobTitles.add(new JobTitle("Title 2", "Description 2"));

        when(jobTitleRepository.findAll()).thenReturn(jobTitles);
        List<JobTitle> result = jobTitleService.getAllJobTitles();
        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Title 2", result.get(1).getTitle());
        assertEquals("Description 1", result.get(0).getDescription());
        assertEquals("Description 2", result.get(1).getDescription());
    }

    @Test
    void createJobTitle() {
        JobTitle jobTitle = new JobTitle("Title1", "Description1");
        when(jobTitleRepository.save(jobTitle)).thenReturn(jobTitle);
        JobTitle result = jobTitleService.createJobTitle(jobTitle);
        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
        assertEquals("Description1", result.getDescription());
    }

    @Test
    void createJobTitleWithDepartment() {
        Long departmentId = 1L;
        Department department = new Department(1L,"DepartmentName","Description1");
        JobTitle jobTitle = new JobTitle("Title1", "Description1");
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(jobTitleRepository.save(jobTitle)).thenReturn(jobTitle);
        JobTitle result = jobTitleService.createJobTitleWithDepartment(departmentId, jobTitle);
        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
        assertEquals("Description1", result.getDescription());
        assertEquals(department, result.getDepartment());
    }

    @Test
    void getAllJobTitlesByDepartmentId() {
        Long departmentId = 1L;
        Department department = new Department(1L,"DepartmentName","Description");
        List<JobTitle> jobTitles = List.of(new JobTitle("Title1", "Description1"));
        when(departmentRepository.existsById(departmentId)).thenReturn(true);
        when(jobTitleRepository.findByDepartmentId(departmentId)).thenReturn(jobTitles);
        List<JobTitle> result = jobTitleService.getAllJobTitlesByDepartmentId(departmentId);
        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    void getJobTitleById() {
        Long jobTitleId = 1L;
        JobTitle jobTitle = new JobTitle("Title1", "Description1");
        when(jobTitleRepository.findById(jobTitleId)).thenReturn(Optional.of(jobTitle));
        JobTitle result = jobTitleService.getJobTitleById(jobTitleId);
        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
        assertEquals("Description1", result.getDescription());
    }

    @Test
    void getJobByTitleContaining() {
        String title = "Title";
        List<JobTitle> jobTitles = List.of(new JobTitle("Title1", "Description1"));
        when(jobTitleRepository.findByTitleContaining(title)).thenReturn(jobTitles);

        List<JobTitle> result = jobTitleService.getJobByTitleContaining(title);
        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    void updateJobTitle() {
        Long jobTitleId = 1L;
        JobTitle existingJobTitle = new JobTitle("Title1", "Description1");
        JobTitle updatedJobTitle = new JobTitle("Title2", "Description2");
        when(jobTitleRepository.findById(jobTitleId)).thenReturn(Optional.of(existingJobTitle));
        when(jobTitleRepository.save(existingJobTitle)).thenReturn(updatedJobTitle);
        JobTitle result = jobTitleService.updateJobTitle(jobTitleId, updatedJobTitle);

        assertNotNull(result);
        assertEquals("Title2", result.getTitle());
        assertEquals("Description2", result.getDescription());
    }

    @Test
    void deleteJobTitle() {
        Long jobTitleId = 1L;
        JobTitle jobTitle = new JobTitle("Title1", "Description1");
        when(jobTitleRepository.findById(jobTitleId)).thenReturn(Optional.of(jobTitle));
        Map<String, Boolean> result = jobTitleService.deleteJobTitle(jobTitleId);
        assertNotNull(result);
        assertTrue(result.get("deleted"));
    }
}