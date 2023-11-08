package com.average.bookReturn.users.otp;

import com.average.bookReturn.users.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp")
public class OTPInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secretKey;
    @OneToOne(mappedBy = "otpInfo")
    private User user;
}

