package com.example.postoffice.exception;

import com.example.postoffice.dto.ApiError;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {
    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<String> listErrors = new ArrayList<>();
        List<FieldError> fieldError = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldError) {
            String defaultMessage = error.getDefaultMessage();
            listErrors.add(defaultMessage);
        }

        ApiError<?> response = new ApiError<>(status, listErrors);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler({EntityNotFoundException.class,DeliveryException.class})
    protected ResponseEntity<ApiError<?>> notFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError<>(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler({DataIntegrityViolationException.class, OptimisticLockingFailureException.class})
    public ResponseEntity<ApiError<?>> saveException(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError<>(HttpStatus.CONFLICT, exception.getCause().getMessage()));
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<ApiError<?>> handleIllegalStateException(IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError<>(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
}
