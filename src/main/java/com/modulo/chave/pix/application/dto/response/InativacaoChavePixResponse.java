package com.modulo.chave.pix.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.infrastructure.web.serializer.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InativacaoChavePixResponse {
    private UUID id;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private TipoContaEnum tipoConta;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class, nullsUsing = LocalDateTimeSerializer.class)
    private LocalDateTime dataInclusao;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class, nullsUsing = LocalDateTimeSerializer.class)
    private LocalDateTime dataInativacao;    
}
