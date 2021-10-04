package com.target.myRetail.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MyRetailExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString());
        log.debug("In MyRetailExceptionHandler handleAllExceptions with - "+ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MyRetailException.class})
    public final ResponseEntity<Object> handleValidationExceptions(
            MyRetailException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getStatus().toString());
        log.debug("In MyRetailExceptionHandler handleValidationExceptions with - "+ex.getMessage());
        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getBindingResult().toString(), HttpStatus.BAD_REQUEST.toString());
        log.debug("In MyRetailExceptionHandler handleMethodArgumentNotValid with - "+ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
