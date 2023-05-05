package com.marcofaccani.app.controller;

import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

  @ExceptionHandler
  public ResponseEntity<?> handleInternalServerError(Exception exception){
    log.info("Exception caught: {}", exception.toString());
    return ResponseEntity.internalServerError().body(exception.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<?> handleValidationError(ConstraintViolationException exception){
    log.info("Exception caught: {}", exception.toString());
    return ResponseEntity.badRequest().body(exception.getMessage());
  }

}
