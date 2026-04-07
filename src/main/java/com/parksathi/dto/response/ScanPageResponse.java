package com.parksathi.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScanPageResponse {

    private String ownerName;
    private String vehicleNumber;
    private Boolean tokenValid;
    private String message;

}
