package com.parksathi.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerResponseRequest {
    private String sessionToken;
    private String responseType;
}
