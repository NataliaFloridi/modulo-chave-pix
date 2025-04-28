package com.modulo.chave.pix.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoContaEnum {
    CORRENTE("corrente"),
    POUPANCA("poupanca");

    private String descricao;
}
