package com.modulo.chave.pix.application.dto.request;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class InclusaoChavePixRequest {
    
    @NotNull(message = "Tipo da chave é obrigatório")
    private TipoChaveEnum tipoChave;

    @NotBlank(message = "Valor da chave é obrigatório")
    private String valorChave;

    @NotNull(message = "Tipo da conta é obrigatório")
    private TipoContaEnum tipoConta;

    @NotNull(message = "Agência é obrigatória")
    @Min(value = 1, message = "Agência deve ter pelo menos 1 dígito numérico")
    @Max(value = 9999, message = "Agência deve ter até 4 dígitos numéricos")
    private Integer numeroAgencia;
    //@Pattern(regexp = "^\\d{1,4}$", message = "Agência deve ter até 4 dígitos numéricos")
    //private String numeroAgencia;

    @NotNull(message = "Conta é obrigatória")
    @Min(value = 1, message = "Conta deve ter pelo menos 1 dígito numérico")
    @Max(value = 99999999, message = "Conta deve ter até 8 dígitos numéricos")
    private Integer numeroConta;
    //@Pattern(regexp = "^\\d{1,8}$", message = "Conta deve ter até 8 dígitos numéricos")
    //private String numeroConta;

    @NotBlank(message = "Nome do correntista é obrigatório")
    @Size(max = 30, message = "Nome deve ter até 30 caracteres")
    private String nomeCorrentista;

    @Size(max = 45, message = "Sobrenome deve ter até 45 caracteres")
    private String sobrenomeCorrentista;
}
