package com.modulo.chave.pix.application.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixValidatorStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ChaveEmailValidator implements ChavePixValidatorStrategy {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public boolean validate(String chaveEmail) throws ValidationException {
        log.info("Validando comprimento da chave email");
        validarComprimento(chaveEmail);
        log.info("Validando formato da chave email");
        validarFormato(chaveEmail);

        log.info("Chave email válida");
        return true;
    }

    private void validarFormato(String chaveEmail) throws ValidationException {

        if (!EMAIL_PATTERN.matcher(chaveEmail).matches()) {
            log.error("Formato inválido. Padrão esperado: [nome@dominio.com]");
            throw new ValidationException("Formato inválido. Padrão esperado: [nome@dominio.com]");
        }

        if (!chaveEmail.contains("@")) {
            log.error("E-mail deve conter o símbolo @");
            throw new ValidationException("E-mail deve conter o símbolo @");
        }
    }

    private void validarComprimento(String chaveEmail) throws ValidationException {
        if (chaveEmail.length() > 77) {
            log.error("E-mail deve conter no máximo 77 caracteres");
            throw new ValidationException("E-mail deve conter no máximo 77 caracteres");
        }
    }
};
