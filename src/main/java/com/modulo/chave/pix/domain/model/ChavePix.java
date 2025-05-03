package com.modulo.chave.pix.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChavePix {
    
    private UUID id;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private TipoContaEnum tipoConta;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
    private TipoPessoaEnum tipoPessoa;
}

