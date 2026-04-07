package com.parksathi.dto.websocket;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactSocketMessage {
    private String type;
    private String message;
}