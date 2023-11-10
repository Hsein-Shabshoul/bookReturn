package com.average.bookReturn.users.user;

import com.average.bookReturn.plans.Plan;
import com.average.bookReturn.users.otp.OTPInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name can not be blank")
    private String firstname;
    private String lastname;
    @Column(unique = true)
    @NotNull(message = "Email can not be NULL")
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Needs to be a valid Email")
    private String email;
    private String password;
    private String status;
    @Column(name = "average_return")
    private Double averageReturn;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "otp_id", referencedColumnName = "id")
    private OTPInfo otpInfo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;
}
