package com.example.employeeProject.employee;
import com.example.employeeProject.jobTitle.JobTitle;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "First Name can't be Null")
    @NotBlank(message = "First Name can't be blank")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last Name can't be Null")
    @NotBlank(message = "Last Name can't be blank")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Needs to be a valid Email")
    @Column(name = "email_id")
    private String emailId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_title_id", nullable = false,referencedColumnName = "id")
    private JobTitle jobTitle;

    public Employee() {}

    public Employee(String firstName, String lastName, String emailId) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }
    @Override
    public String toString(){
        return  "Employee Details{"+
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
