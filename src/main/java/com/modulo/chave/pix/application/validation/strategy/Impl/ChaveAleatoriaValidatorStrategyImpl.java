package com.modulo.chave.pix.application.validation.strategy.Impl;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixTipoValidatorStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ChaveAleatoriaValidatorStrategyImpl implements ChavePixTipoValidatorStrategy {

  @Override
  public boolean validate(String chaveAleatoria) throws ValidationException {

      log.info("Validando comprimento da chave aleatória");
      validarComprimento(chaveAleatoria);

      log.info("Chave aleatória válida");
      return true;
  }

  private void validarComprimento(String chaveAleatoria) throws ValidationException {
      if (chaveAleatoria.length() > 36) {
          log.error("Chave aleatória deve conter no máximo 36 dígitos");
          throw new ValidationException("Chave aleatória deve conter no máximo 36 dígitos");
      }
  }
}