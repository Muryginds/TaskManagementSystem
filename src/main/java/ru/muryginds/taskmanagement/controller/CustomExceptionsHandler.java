package ru.muryginds.taskmanagement.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.muryginds.taskmanagement.dto.response.ErrorResponseDTO;
import ru.muryginds.taskmanagement.exception.TaskManagerException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionsHandler {
    @ExceptionHandler({
            ConstraintViolationException.class,
            TaskManagerException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            ExpiredJwtException.class,
            InternalAuthenticationServiceException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleCustomExceptions(Exception e) {
        return ResponseEntity.badRequest().body(prepareErrorResponse(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(prepareErrorResponse(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleArgumentNotValidationException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(String.format("%s - %s", fieldName, errorMessage));
        });
        return ResponseEntity.badRequest().body(prepareErrorResponse(errors));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodValidationException(HandlerMethodValidationException e) {
        List<String> errors = new ArrayList<>();
        e.getAllValidationResults().forEach(error -> {
            String fieldName = error.getMethodParameter().getParameterName();
            error.getResolvableErrors().forEach(message ->
                    errors.add(String.format("%s - %s", fieldName, message.getDefaultMessage()))
            );
        });
        return ResponseEntity.badRequest().body(prepareErrorResponse(errors));
    }

    private ErrorResponseDTO prepareErrorResponse(Exception e) {
        return ErrorResponseDTO.builder()
                .errors(List.of(e.getLocalizedMessage()))
                .build();
    }

    private ErrorResponseDTO prepareErrorResponse(List<String> list) {
        return ErrorResponseDTO.builder()
                .errors(list)
                .build();
    }
}
