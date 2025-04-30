package com.modulo.chave.pix.application.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de consulta de chaves PIX.
 * 
 * Padrão: Builder
 * Motivo: Para facilitar a criação de objetos com muitos parâmetros opcionais.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaChavePixRequest {
    
    private UUID id;
    private TipoChaveEnum tipoChave;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
} 