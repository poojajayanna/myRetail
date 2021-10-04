package com.target.myRetail.exception;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Pooja Jayanna
 * @version 1.0
 */
@Data
public class ErrorDetails {

    private String message;

    private String code;

    private LocalDate date;

    public ErrorDetails(String message, String status) {
        this.message = message;
        this.code = status;
        this.date = LocalDate.now();
    }
}
