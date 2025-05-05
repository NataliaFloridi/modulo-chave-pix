package com.modulo.chave.pix.application.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaChavePixRequest {
    
    private UUID id;
    private TipoChaveEnum tipoChave;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private String nomeCorrentista;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
} 