package com.mercadolibre.fernandez_federico.controller;

import ch.qos.logback.core.joran.spi.DefaultClass;
import com.mercadolibre.fernandez_federico.exceptions.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    protected ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errorMessages = e.getConstraintViolations().stream().map(x -> x.getMessage()).collect(Collectors.toList());
        ApiError apiError = new ApiError(BAD_REQUEST.name(), errorMessages, BAD_REQUEST.value());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    protected ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        ApiError apiError = new ApiError(BAD_REQUEST.name(), errorMessages, BAD_REQUEST.value());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }
}
