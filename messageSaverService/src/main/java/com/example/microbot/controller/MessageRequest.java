package com.example.microbot.controller;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Introspected
@Serdeable
public class MessageRequest {
    private String username;
    private String text;
}
