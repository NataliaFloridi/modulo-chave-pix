package com.modulo.chave.pix.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoContaEnum {
    CONTA_CORRENTE("corrente"),
    CONTA_POUPANCA("poupança");

    private String descricao;
}
