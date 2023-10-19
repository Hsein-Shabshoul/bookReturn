package com.example.employeeProject.jobTitle;
import com.example.employeeProject.department.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_titles")
public class JobTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Job title can not be null")
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

//    @OneToMany(mappedBy = "jobTitle")//fix overflow error using lazy
//    private List<Employee> employee;

    public JobTitle(){} //to work with get requests

    public JobTitle(String title, String description){
        super();
        this.title = title;
        this.description = description;
    }
}
