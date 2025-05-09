package com.modulo.chave.pix.application.validation.strategy.Impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixTipoValidatorStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ChaveCelularValidatorStrategyImpl implements ChavePixTipoValidatorStrategy {

    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\+(\\d{1,2})\\((\\d{1,3})\\)(\\d{4,5}-\\d{4})$");

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

        if (!chaveCelular.startsWith("+")) {
            log.error("O número de celular deve começar com '+'");
            throw new ValidationException("O número de celular deve começar com '+'");
        }

        if (!chaveCelular.contains("(") || !chaveCelular.contains(")")) {
            log.error("O DDD deve estar entre parênteses");
            throw new ValidationException("O DDD deve estar entre parênteses");
        }

        if (!chaveCelular.contains("-")) {
            log.error("O número deve conter hífen separando os dígitos");
            throw new ValidationException("O número deve conter hífen separando os dígitos");
        }
    }

    private void validarCodigoPais(String codigoPais) throws ValidationException {
        if (codigoPais.length() < 1 || codigoPais.length() > 2) {
            log.error("Código do país deve ter entre 1 e 2 dígitos");
            throw new ValidationException("Código do país deve ter entre 1 e 2 dígitos");
        }
        if (!codigoPais.matches("\\d+")) {
            log.error("Código do país deve conter apenas números");
            throw new ValidationException("Código do país deve conter apenas números");
        }
    }

    private void validarDDD(String ddd) throws ValidationException {
        if (ddd.length() < 1 || ddd.length() > 3) {
            log.error("DDD deve ter entre 1 e 3 dígitos");
            throw new ValidationException("DDD deve ter entre 1 e 3 dígitos");
        }
        if (!ddd.matches("\\d+")) {
            log.error("DDD deve conter apenas números");
            throw new ValidationException("DDD deve conter apenas números");
        }
    }

    private void validarNumero(String numeroCelular) throws ValidationException {
        if (numeroCelular.length() != 9) {
            log.error("Número deve conter exatamente 9 dígitos");
            throw new ValidationException("Número deve conter exatamente 9 dígitos");
        }
        if (!numeroCelular.matches("\\d+")) {
            log.error("Número deve conter apenas números");
            throw new ValidationException("Número deve conter apenas números");
        }
    }

    private String[] extrairComponentes(String chaveCelular) throws ValidationException {
        Matcher matcher = CELULAR_PATTERN.matcher(chaveCelular);
        
        if (!matcher.matches()) {
            log.error("Formato inválido. Padrão esperado: +55(11)99999-9999");
            throw new ValidationException("Formato inválido. Padrão esperado: +55(11)99999-9999");
        }
        
        String codigoPais = matcher.group(1);
        String ddd = matcher.group(2);
        String numero = matcher.group(3).replace("-", "");
        return new String[] { codigoPais, ddd, numero };
    }
}
