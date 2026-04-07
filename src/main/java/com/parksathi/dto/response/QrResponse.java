package com.parksathi.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrResponse {

    private Long vehicleId;
    private String qrToken;
    private String qrImageBase64;
}
