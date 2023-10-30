package com.example.employeeProject.security.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    @Test
    public void testEmptyConstructer(){
        RegisterRequest request = new RegisterRequest();
        assertEquals(request,new RegisterRequest());
    }
}