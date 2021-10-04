package com.target.myRetail.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class MyRetailExceptionHandler {

    @ExceptionHandler({MyRetailException.class})
    public final ResponseEntity<Object> handleValidationExceptions(
            MyRetailException ex) {
        Error error = new Error(ex.getMessage(), ex.getStatus().toString());
        return new ResponseEntity<>(error, ex.getStatus());
    }


}
