package com.modulo.chave.pix.application.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixValidatorStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ChaveCnpjValidator implements ChavePixValidatorStrategy {

     // validação do formato do cnpj via Regex para somente numeros
     private static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{14}$");
 
     @Override
     public boolean validate(String chaveCnpj) throws ValidationException {
        log.info("Validando formato da chave CNPJ");
         validarFormato(chaveCnpj);
         log.info("Validando comprimento da chave CNPJ");
         validarComprimento(chaveCnpj);
         log.info("Validando CNPJ válido");
         validarCnpjValido(chaveCnpj);
 
         return true;
     }
 
     private void validarFormato(String chaveCnpj) throws ValidationException {
 
         if (chaveCnpj == null || chaveCnpj.isBlank()) {
             log.error("CNPJ não pode ser vazio");
             throw new ValidationException("CNPJ não pode ser vazio");
         }
         // verifica se o formato do email é válido, aqui já se verifica se começa com
         if (!CNPJ_PATTERN.matcher(chaveCnpj).matches()) {
             log.error("Formato inválido. Deve conter somente números");
             throw new ValidationException("Formato inválido. Deve conter somente números");
         }
     }
 
     // validação do comprimento do cpf ???? duvidas
     private void validarComprimento(String chaveCnpj) throws ValidationException {
         if (chaveCnpj.length() > 14) {
             log.error("CNPJ deve conter exatamente 14 dígitos");
             throw new ValidationException("CNPJ deve conter exatamente 14 dígitos");
         }
     }
 
     private void validarCnpjValido(String chaveCnpj) throws ValidationException {
         CNPJValidator cnpjValidator = new CNPJValidator();
 
         try {
             cnpjValidator.assertValid(chaveCnpj);
         } catch (InvalidStateException e) {
             log.error("CNPJ inválido");
             throw new ValidationException("CNPJ inválido");
         }
 
    }
}
