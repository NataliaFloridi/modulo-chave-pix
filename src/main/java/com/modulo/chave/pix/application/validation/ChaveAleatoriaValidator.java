package com.modulo.chave.pix.application.validation;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixValidatorStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ChaveAleatoriaValidator implements ChavePixValidatorStrategy {


  @Override
  public boolean validate(String chaveAleatoria) throws ValidationException {
      log.info("Validando formato da chave aleatória");
      validarFormato(chaveAleatoria);
      log.info("Validando comprimento da chave aleatória");
      validarComprimento(chaveAleatoria);

      log.info("Chave aleatória válida");
      return true;
  }

  private void validarFormato(String chaveAleatoria) throws ValidationException {

      if (chaveAleatoria == null || chaveAleatoria.isBlank()) {
          log.error("Chave aleatória não pode ser vazia");
          throw new ValidationException("Chave aleatória não pode ser vazia");
      }
  }

  private void validarComprimento(String chaveAleatoria) throws ValidationException {
      if (chaveAleatoria.length() > 36) {
          log.error("Chave aleatória deve conter no máximo 36 dígitos");
          throw new ValidationException("Chave aleatória deve conter no máximo 36 dígitos");
      }
  }
}