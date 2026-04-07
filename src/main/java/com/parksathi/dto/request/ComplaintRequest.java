package com.parksathi.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintRequest {

    private String sessionToken;
    private String reason;
    private Double latitude;
    private Double longitude;
    private String address;
}