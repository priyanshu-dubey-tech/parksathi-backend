package com.parksathi.service;

import com.parksathi.dto.request.AuthRequest;
import com.parksathi.dto.request.LoginRequest;
import com.parksathi.dto.response.AuthResponse;
import com.parksathi.dto.response.LoginResponse;

public interface AuthService {

    AuthResponse register(AuthRequest request);

    LoginResponse login(LoginRequest request);
}