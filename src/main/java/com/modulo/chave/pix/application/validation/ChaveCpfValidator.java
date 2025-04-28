package com.modulo.chave.pix.application.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixValidatorStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ChaveCpfValidator implements ChavePixValidatorStrategy {

    // validação do formato do cpf via Regex para somente numeros
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

    @Override
    public boolean validate(String chaveCpf) throws ValidationException {
        log.info("Validando formato da chave CPF");
        validarFormato(chaveCpf);
        log.info("Validando comprimento da chave CPF");
        validarComprimento(chaveCpf);
        log.info("Validando CPF válido");
        validarCpfValido(chaveCpf);

        return true;
    }

    private void validarFormato(String chaveCpf) throws ValidationException {

        if (chaveCpf == null || chaveCpf.isBlank()) {
            log.error("CPF não pode ser vazio");
            throw new ValidationException("CPF não pode ser vazio");
        }
        // verifica se o formato do email é válido, aqui já se verifica se começa com
        if (!CPF_PATTERN.matcher(chaveCpf).matches()) {
            log.error("Formato inválido. Deve conter somente números");
            throw new ValidationException("Formato inválido. Deve conter somente números");
        }
    }

    // validação do comprimento do cpf ???? duvidas
    private void validarComprimento(String chaveCpf) throws ValidationException {
        if (chaveCpf.length() > 11) {
            log.error("CPF deve conter exatamente 11 dígitos");
            throw new ValidationException("CPF deve conter exatamente 11 dígitos");
        }
    }

    private void validarCpfValido(String chaveCpf) throws ValidationException {
        CPFValidator cpfValidator = new CPFValidator();

        try {
            cpfValidator.assertValid(chaveCpf);
        } catch (InvalidStateException e) {
            log.error("CPF inválido");
            throw new ValidationException("CPF inválido");
        }

    }
}
