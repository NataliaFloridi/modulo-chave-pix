package com.modulo.chave.pix.application.dto.request;

import java.util.UUID;

import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlteracaoContaPixRequest {

    @NotNull(message = "O ID da chave é obrigatório")
    private UUID id;

    @NotNull(message = "Tipo da conta é obrigatório")
    private TipoContaEnum tipoConta;

    @NotBlank(message = "Agência é obrigatória")
    @Pattern(regexp = "^\\d{1,4}$", message = "Agência deve ter até 4 dígitos numéricos")
    private String numeroAgencia;

    @NotBlank(message = "Conta é obrigatória")
    @Pattern(regexp = "^\\d{1,8}$", message = "Conta deve ter até 8 dígitos numéricos")
    private String numeroConta;

    @NotBlank(message = "O nome do correntista é obrigatório")
    @Size(max = 30, message = "Nome deve ter até 30 caracteres")
    private String nomeCorrentista;

    @Size(max = 45, message = "Sobrenome deve ter até 45 caracteres")
    private String sobrenomeCorrentista;
}