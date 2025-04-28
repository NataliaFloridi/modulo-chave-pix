package com.modulo.chave.pix.application.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixValidatorStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ChaveCelularValidator implements ChavePixValidatorStrategy {

    // validação do formato do celular via Regex
    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\+[1-9]\\d{1,2}\\d{9}$");


    @Override
    public boolean validate(String chaveCelular) throws ValidationException {
        log.info("Validando formato da chave celular");
        validarFormato(chaveCelular);

        log.info("Extraindo componentes da chave celular");
        String[] componentes = extrairComponentes(chaveCelular);
        String codigoPais = componentes[0];
        String ddd = componentes[1];
        String numero = componentes[2];

        log.info("Validando código do país");
        validarCodigoPais(codigoPais);

        log.info("Validando DDD");
        validarDDD(ddd);

        log.info("Validando número");
        validarNumero(numero);

        log.info("Chave celular válida");
        return true;
    }

    private void validarFormato(String chaveCelular) throws ValidationException {

        if (chaveCelular == null || chaveCelular.isBlank()) {
            log.error("Número de celular não pode ser vazio");
            throw new ValidationException("Número de celular não pode ser vazio");
        }
        // verifica se o formato do celular é válido, aqui já se verifica se começa com
        // + e tem 11 dígitos
        if (!CELULAR_PATTERN.matcher(chaveCelular).matches()) {
            log.error("Formato inválido. Padrão esperado: +[Cód.País][DDD][Número]");
            throw new ValidationException("Formato inválido. Padrão esperado: +[Cód.País][DDD][Número]");
        }
    }

    private void validarCodigoPais(String codigoPais) throws ValidationException {
        // verifica se o código do país tem entre 1 e 2 dígitos
        if (codigoPais.length() < 1 || codigoPais.length() > 2) {
            log.error("Código do país deve ter entre 1 e 2 dígitos");
            throw new ValidationException("Código do país deve ter entre 1 e 2 dígitos");
        }
    }

    private void validarDDD(String ddd) throws ValidationException {
        // verifica se o DDD tem entre 1 e 3 dígitos
        if (ddd.length() < 1 || ddd.length() > 3) {
            log.error("DDD deve ter entre 1 e 3 dígitos");
            throw new ValidationException("DDD deve ter entre 1 e 3 dígitos");
        }
    }

    private void validarNumero(String numeroCelular) throws ValidationException {
        // verifica se o número tem exatamente 9 dígitos
        if (numeroCelular.length() != 9) {
            log.error("Número deve conter exatamente 9 dígitos");
            throw new ValidationException("Número deve conter exatamente 9 dígitos");
        }
    }

    private String[] extrairComponentes(String chaveCelular) {
        log.info("Extraindo componentes da chave celular");
        Matcher matcher = CELULAR_PATTERN.matcher(chaveCelular);
        matcher.find();
        return new String[] {
                matcher.group(1), 
                matcher.group(2), 
                matcher.group(3) 
        };
    }
}
