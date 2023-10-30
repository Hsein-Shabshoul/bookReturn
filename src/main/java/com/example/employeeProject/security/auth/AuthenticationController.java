package com.example.employeeProject.security.auth;

import com.example.employeeProject.exception.BadCredentialsE;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) throws BadCredentialsE {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register_role")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestWithRole request) throws BadCredentialsE {
        return ResponseEntity.ok(service.registerWithRole(request));
    }
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("From employeeProject api/v1");
    }
}
