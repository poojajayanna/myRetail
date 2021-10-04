package com.target.myRetail.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Error {

    private String message;

    private String code;

    public Error(String message, String status) {
        this.message = message;
        this.code = status;
    }
}
