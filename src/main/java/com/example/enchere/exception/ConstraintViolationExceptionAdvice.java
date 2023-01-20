package com.example.enchere.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ConstraintViolationExceptionAdvice extends ResponseEntityExceptionHandler {


}
