package com.example.employeeProject.security.auth;


import com.example.employeeProject.security.config.JwtService;
import com.example.employeeProject.exception.BadCredentialsE;
import com.example.employeeProject.security.Role;
import com.example.employeeProject.security.User;
import com.example.employeeProject.security.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) throws BadCredentialsE {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                //.role(Role.ROLE_ADMIN)
                .build();
        if(repository.findByEmail(request.getEmail()).isPresent())
            throw new BadCredentialsE("Email already exists.");
        repository.save(user);
        String jwtToken = jwtService.generateToken(user,user.getRole().name());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public @NotBlank(message = "Name can not be blank") String registerWithRole(RegisterRequestWithRole request) throws BadCredentialsE {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        if(repository.findByEmail(request.getEmail()).isPresent())
            throw new BadCredentialsE("Email already exists.");
        repository.save(user);
        var jwtToken = jwtService.generateToken(user,user.getRole().name());
        return "New user added: \n"+user.toString();
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
                    .orElseThrow(() -> new BadCredentialsE("User not found."));
        } catch (BadCredentialsE e) {
            throw new RuntimeException(e);
        }

        var jwtToken = jwtService.generateToken(user,user.getRole().name());
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
