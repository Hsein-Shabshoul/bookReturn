package com.average.bookReturn.users.user;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "average_return")
    private Double averageReturn;
}
