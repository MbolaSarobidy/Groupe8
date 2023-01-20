package com.example.enchere.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<Object> error(HttpException e){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", new HttpExceptionAdapter(e));
        return new ResponseEntity<>(body, e.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> error(ConstraintViolationException e){
        Map<String, Object> body = new LinkedHashMap<>();
        ConstraintViolation constraintViolation = e.getConstraintViolations().iterator().next();
        HttpException httpException = new HttpException(HttpStatus.BAD_REQUEST, StringUtils.capitalize(constraintViolation.getPropertyPath().toString())+" "+ constraintViolation.getMessage());
        body.put("error", new HttpExceptionAdapter(httpException));
        return new ResponseEntity<>(body, httpException.getStatus());
    }

}
