package com.parksathi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_sessions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vehicleId;
    private Long ownerUserId;

    // Rahul side (scanner)
    private String scannerMobile;

    private String sessionToken;

    private String reason; // BLOCKING / NO_PARKING / EMERGENCY

    private String status; // ACTIVE / COMPLETED / EXPIRED / NO_RESPONSE

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiresAt;

    private Boolean callAttempted = false;
    private Boolean alterSent = false;
    private Boolean complaintAllowed = false;
    private LocalDateTime lastAttemptedAt;
    private String ownerResponse; // ACCEPTED / REJECTED / NO_RESPONSE
    private LocalDateTime ownerRespondedAt;
}
