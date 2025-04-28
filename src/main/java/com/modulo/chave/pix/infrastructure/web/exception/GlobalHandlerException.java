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

import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.DuplicateKeyException;
import com.modulo.chave.pix.domain.exception.ValidationException;

@ControllerAdvice
public class GlobalHandlerException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalHandlerException.class);

    @ExceptionHandler({ValidationException.class, BusinessValidationException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        logger.warn("Erro de validação: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));
        
        logger.warn("Erro de validação de argumentos: {}", errorMsg);
        return ResponseEntity.unprocessableEntity()
            .body(new ErrorResponse("Erro de validação: " + errorMsg));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.warn("Erro ao ler requisição: {}", ex.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("Formato de requisição inválido"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        logger.warn("Método HTTP não suportado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        logger.warn("Parâmetro obrigatório ausente: {}", ex.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("Parâmetro obrigatório ausente: " + ex.getParameterName()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        String errorId = java.util.UUID.randomUUID().toString();
        logger.error("Erro não tratado [{}]: {}", errorId, ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
            .body(new ErrorResponse("Erro interno no servidor. ID: " + errorId));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        logger.warn("Erro de status HTTP: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode())
            .body(new ErrorResponse(ex.getReason()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException ex) {
        logger.warn("Tentativa de criar chave duplicada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Erro de argumento ilegal: {}", ex.getMessage(), ex);
        
        if (ex.getMessage() != null && ex.getMessage().contains("No enum constant")) {
            String mensagem = "Valor inválido para o enum. Verifique os valores permitidos.";
            return ResponseEntity.badRequest().body(new ErrorResponse(mensagem, LocalDateTime.now()));
        }
        
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()));
    }
}
