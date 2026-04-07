package com.parksathi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vehicleId;
    private Long ownerUserId;
    private Long victimUserId;

    private String sessionToken;

    private String reason;

    private String imageUrl;

    private Double latitude;
    private Double longitude;

    private String address;

    private String status; // CREATED, UNDER_REVIEW, RESOLVED

    private LocalDateTime createdAt;

    private Boolean deleted = false;
}
