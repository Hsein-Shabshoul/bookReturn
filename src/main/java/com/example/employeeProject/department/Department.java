package com.example.employeeProject.department;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Department name can not be null")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

//    public Department(String name, String description){
//        super();
//        this.name = name;
//        this.description = description;
//    }
}

