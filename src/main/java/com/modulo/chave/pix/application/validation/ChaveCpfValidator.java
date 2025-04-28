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

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final CPFValidator CPF_VALIDATOR = new CPFValidator();

    @Override
    public boolean validate(String chaveCpf) throws ValidationException {
        log.info("Validando comprimento da chave CPF");
        validarComprimento(chaveCpf);
        log.info("Validando formato da chave CPF");
        validarFormato(chaveCpf);
        log.info("Validando CPF válido");
        validarCpfValido(chaveCpf);

        return true;
    }

    private void validarFormato(String chaveCpf) throws ValidationException {
        if (!CPF_PATTERN.matcher(chaveCpf).matches()) {
            log.error("Formato inválido. Deve conter somente números");
            throw new ValidationException("Formato inválido. Deve conter somente números");
        }
    }

    private void validarComprimento(String chaveCpf) throws ValidationException {
        if (chaveCpf.length() > 11) {
            log.error("CPF deve conter no máximo 11 dígitos");
            throw new ValidationException("CPF deve conter no máximo 11 dígitos");
        }
    }

    private void validarCpfValido(String chaveCpf) throws ValidationException {
        try {
            CPF_VALIDATOR.assertValid(chaveCpf);
        } catch (InvalidStateException e) {
            log.error("CPF inválido");
            throw new ValidationException("CPF inválido");
        }
    }
}
