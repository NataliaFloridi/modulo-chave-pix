package com.modulo.chave.pix.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CriarChavePixResponse {
    
    private UUID id;
    private LocalDateTime dataCriacao;
}
