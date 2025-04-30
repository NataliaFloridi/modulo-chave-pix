package com.modulo.chave.pix.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AlteracaoChavePixResponse {
    
    private UUID id;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private TipoContaEnum tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private LocalDateTime dataInclusao;
}
