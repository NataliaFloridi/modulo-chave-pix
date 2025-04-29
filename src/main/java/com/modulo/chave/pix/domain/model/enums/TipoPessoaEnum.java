package com.modulo.chave.pix.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPessoaEnum {
    FISICA ("fisica"),
    JURIDICA ("juridica");

    private String descricao;
} 