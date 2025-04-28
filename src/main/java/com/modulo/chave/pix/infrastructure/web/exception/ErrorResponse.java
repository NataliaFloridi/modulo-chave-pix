package com.modulo.chave.pix.infrastructure.web.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ErrorResponse(String message, @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime timestamp) {
    public ErrorResponse(String message) {
        this(message, LocalDateTime.now());
    }
}


