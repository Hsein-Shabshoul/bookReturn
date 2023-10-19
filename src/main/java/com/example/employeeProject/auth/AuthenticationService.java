package com.example.employeeProject.auth;


import com.example.employeeProject.config.JwtService;
import com.example.employeeProject.exception.BadCredentialsE;
import com.example.employeeProject.exception.EmployeeNotFoundException;
import com.example.employeeProject.exception.ResourceNotFoundException;
import com.example.employeeProject.model.Role;
import com.example.employeeProject.model.User;
import com.example.employeeProject.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                //.role(Role.USER)
                .role(Role.ROLE_ADMIN)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user,user.getRole().name());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = null;
        try {
            user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsE("User not found "));
        } catch (BadCredentialsE e) {
            throw new RuntimeException(e);
        }

        var jwtToken = jwtService.generateToken(user,user.getRole().name());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
