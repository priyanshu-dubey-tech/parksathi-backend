package com.parksathi.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrScanRequest {

    private String qrToken;
    private String scannerMobile;
    private String reason;

}