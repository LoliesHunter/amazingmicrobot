package com.example.botClient.DTO;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Serdeable
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private String username;
    private String text;
}