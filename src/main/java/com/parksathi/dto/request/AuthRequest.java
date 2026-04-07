package com.parksathi.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String firstName;
    private String mobile;
    private String password;
    private String email;
    private String otp;
}