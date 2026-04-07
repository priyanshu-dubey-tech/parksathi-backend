package com.parksathi.controller;

import com.parksathi.dto.request.AuthRequest;
import com.parksathi.dto.request.LoginRequest;
import com.parksathi.dto.response.AuthResponse;
import com.parksathi.dto.response.LoginResponse;
import com.parksathi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

}
