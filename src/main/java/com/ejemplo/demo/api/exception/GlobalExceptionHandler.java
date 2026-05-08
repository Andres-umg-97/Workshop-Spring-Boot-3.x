package com.ejemplo.demo.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBusinessError(IllegalArgumentException ex) {
        return Map.of(
                "codigo", "BUSINESS_RULE_ERROR",
                "mensaje", ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationError(MethodArgumentNotValidException ex) {
        return Map.of(
                "codigo", "VALIDATION_ERROR",
                "mensaje", ex.getBindingResult().getFieldError().getDefaultMessage()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(EntityNotFoundException ex) {
        return Map.of(
                "codigo", "NOT_FOUND",
                "mensaje", ex.getMessage()
        );
    }
}
