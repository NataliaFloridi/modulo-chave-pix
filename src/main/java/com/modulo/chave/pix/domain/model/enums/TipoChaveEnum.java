package com.modulo.chave.pix.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoChaveEnum {
    CELULAR("celular"),
    EMAIL("email"),
    CPF("cpf"),
    CNPJ("cnpj"),
    ALEATORIO("aleatorio");
    
    private String descricao;
}
