package com.parksathi.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerResponseAck {
    private String message;
    private String responseType;
}
