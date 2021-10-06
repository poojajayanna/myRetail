package com.target.myRetail.exception.handler;

import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.exception.dto.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global Exception handler.
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
public class MyRetailExceptionHandler {
    /**
     * Handles all Exceptions
     *
     * @param ex - all Exceptions
     * @return ResponseEntity with status code INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString());
        log.debug("In MyRetailExceptionHandler handleAllExceptions with - "+ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all MyRetailException
     *
     * @param ex - Custom Exception
     * @return ResponseEntity with status code
     */
    @ExceptionHandler({MyRetailException.class})
    public final ResponseEntity<Object> handleValidationExceptions(
            MyRetailException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getStatus().toString());
        log.debug("In MyRetailExceptionHandler handleValidationExceptions with - "+ex.getMessage());
        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

    /**
     * Handles all MethodArgumentNotValidException for validations
     *
     * @param ex - Validation Exception
     * @return ResponseEntity with status code BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> message = ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails(message.toString(), HttpStatus.BAD_REQUEST.toString());
        log.debug("In MyRetailExceptionHandler handleMethodArgumentNotValid with - "+ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
