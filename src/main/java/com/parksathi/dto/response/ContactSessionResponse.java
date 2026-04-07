package com.parksathi.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactSessionResponse {

    private String ownerName;
    private String sessionToken;
    private LocalDateTime expiresAt;
    private String status;
    private Boolean complaintAllowed;

}