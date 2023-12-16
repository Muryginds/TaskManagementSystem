package ru.muryginds.taskmanagement.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.muryginds.taskmanagement.exception.TaskManagerException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionsHandler {
    @ExceptionHandler({
            ConstraintViolationException.class,
            TaskManagerException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            ExpiredJwtException.class
    })
    public ResponseEntity<Object> handleCustomExceptions(Exception e) {
        return ResponseEntity.badRequest().body(getResponse(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(getResponse(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleArgumentNotValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Object> handleMethodValidationException(HandlerMethodValidationException e) {
        Map<String, String> errors = new HashMap<>();
        e.getAllValidationResults().forEach(error -> {
            String fieldName = error.getMethodParameter().getParameterName();
            error.getResolvableErrors().forEach(message ->
                    errors.put(fieldName, message.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getResponse(e));
    }

    private HashMap<Object, Object> getResponse(Exception e) {
        var errorMap = new HashMap<>();
        errorMap.put("Ошибка", e.getLocalizedMessage());
        return errorMap;
    }
}
