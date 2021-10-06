package com.target.myRetail.exception;

import com.target.myRetail.exception.handler.MyRetailExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Pooja Jayanna
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class MyRetailExceptionHandlerUnitTest {

    @InjectMocks
    MyRetailExceptionHandler myRetailExceptionHandler;

    @Test
    public void testHandleAllExceptions() {
        ResponseEntity<Object> responseEntity = myRetailExceptionHandler.handleAllExceptions(new Exception());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testHandleValidationExceptions() {
        ResponseEntity<Object> responseEntity = myRetailExceptionHandler.handleValidationExceptions(new MyRetailException("Parameter missing", HttpStatus.BAD_REQUEST));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
