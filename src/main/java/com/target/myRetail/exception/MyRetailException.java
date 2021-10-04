package com.target.myRetail.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author Pooja Jayanna
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyRetailException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public MyRetailException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
