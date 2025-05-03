package com.modulo.chave.pix.application.dto.request;

import java.util.UUID;

import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class AlteracaoContaPixRequest {

    @NotNull(message = "O ID da chave é obrigatório")
    private UUID id;

    @NotNull(message = "Tipo da conta é obrigatório")
    private TipoContaEnum tipoConta;

    @NotNull(message = "Agência é obrigatória")
    @Min(value = 1, message = "Agência deve ter pelo menos 1 dígito numérico")
    @Max(value = 9999, message = "Agência deve ter até 4 dígitos numéricos")
    private Integer numeroAgencia;

    @NotNull(message = "Conta é obrigatória")
    @Min(value = 1, message = "Conta deve ter pelo menos 1 dígito numérico")
    @Max(value = 99999999, message = "Conta deve ter até 8 dígitos numéricos")
    private Integer numeroConta;

    @NotBlank(message = "O nome do correntista é obrigatório")
    @Size(max = 30, message = "Nome deve ter até 30 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras e espaços")
    private String nomeCorrentista;

    @Size(max = 45, message = "Sobrenome deve ter até 45 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]*$", message = "Sobrenome deve conter apenas letras e espaços")
    private String sobrenomeCorrentista;
}