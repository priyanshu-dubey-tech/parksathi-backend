package com.parksathi.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionHistoryResponse {
    private String vehicleNumber;
    private String scannerMobile;
    private String reason;
    private String status;
    private String ownerResponse;
    private LocalDateTime createdAt;
    private Double latitude;
    private Double longitude;
    private String address;
}
