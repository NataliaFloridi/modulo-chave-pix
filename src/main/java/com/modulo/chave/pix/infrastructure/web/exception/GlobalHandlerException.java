package com.modulo.chave.pix.infrastructure.web.exception;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.convert.ConversionFailedException;

import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.RegistroNotFoundException;
import com.modulo.chave.pix.domain.exception.DuplicateKeyException;
import com.modulo.chave.pix.domain.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Erro de argumento inválido: {}", ex.getMessage());
        
        if (ex.getMessage() != null && ex.getMessage().contains("Data inválida")) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getMessage()));
        }
        
        if (ex.getMessage() != null && ex.getMessage().contains("No enum constant")) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Valor inválido para o enum. Verifique os valores permitidos."));
        }
        
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorResponse> handleConversionFailedException(ConversionFailedException ex) {
        log.warn("Erro de conversão: {}", ex.getMessage());
        
        if (ex.getCause() instanceof IllegalArgumentException) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(ex.getCause().getMessage()));
        }
        
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("Erro ao converter valor: " + ex.getValue()));
    }
    
    @ExceptionHandler({ValidationException.class, BusinessValidationException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(ValidationException ex) {
        log.warn("Erro de validação: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));
        
        log.warn("Erro de validação de argumentos: {}", errorMsg);
        return ResponseEntity.unprocessableEntity()
            .body(new ErrorResponse("Erro de validação: " + errorMsg));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Erro ao ler requisição: {}", ex.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("Formato de requisição inválido"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("Método HTTP não suportado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        log.warn("Parâmetro obrigatório ausente: {}", ex.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("Parâmetro obrigatório ausente: " + ex.getParameterName()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        log.warn("Erro de status HTTP: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode())
            .body(new ErrorResponse(ex.getReason()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException ex) {
        log.warn("Tentativa de criar chave duplicada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(RegistroNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRegistroNotFoundException(RegistroNotFoundException ex) {
        log.error("Registro não encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        String errorId = java.util.UUID.randomUUID().toString();
        log.error("Erro não tratado [{}]: {}", errorId, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("Erro interno no servidor. ID: " + errorId));
    }

    public record ErrorResponse(String message, LocalDateTime timestamp) {
        public ErrorResponse(String message) {
            this(message, LocalDateTime.now());
        }
    }
}
