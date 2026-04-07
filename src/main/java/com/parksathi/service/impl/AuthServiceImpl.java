package com.parksathi.service.impl;

import com.parksathi.dto.request.AuthRequest;
import com.parksathi.dto.request.LoginRequest;
import com.parksathi.dto.response.AuthResponse;
import com.parksathi.dto.response.LoginResponse;
import com.parksathi.entity.EmailOtp;
import com.parksathi.entity.User;
import com.parksathi.repository.EmailOtpRepository;
import com.parksathi.repository.UserRepository;
import com.parksathi.security.JwtUtil;
import com.parksathi.service.AuthService;
import com.parksathi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailOtpRepository emailOtpRepository;


    /*@Override
    public AuthResponse register(AuthRequest request) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setMobile(request.getMobile());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = repository.save(user);

        String token = jwtUtil.generateToken(saved.getMobile());

        return new AuthResponse(
                saved.getId(),
                "User registered successfully",
                token
        );
    }*/

    @Override
    public AuthResponse register(AuthRequest request) {

        if (request.getOtp() == null || request.getOtp().isBlank()) {

            String generatedOtp = String.valueOf(
                    new Random().nextInt(900000) + 100000
            );

            EmailOtp emailOtp = new EmailOtp();
            emailOtp.setEmail(request.getEmail());
            emailOtp.setOtp(generatedOtp);
            emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            emailOtp.setVerified(false);

            emailOtpRepository.save(emailOtp);

            emailService.sendOtp(
                    request.getEmail(),
                    generatedOtp
            );

            return new AuthResponse(
                    null,
                    "OTP sent to email. Please verify to complete registration",
                    null
            );
        }

// STEP 2 → verify OTP
        EmailOtp savedOtp = emailOtpRepository
                .findTopByEmailOrderByIdDesc(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("OTP not found"));

        if (savedOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!savedOtp.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

// save user after OTP verification
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setVerified(true);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getMobile());

        return new AuthResponse(
                user.getId(),
                "User registered successfully",
                token
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getMobile(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getMobile());

        return new LoginResponse(
                token,
                "Login successful"
        );
    }


}
