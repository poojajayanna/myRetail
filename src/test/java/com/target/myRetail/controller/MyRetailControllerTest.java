package com.target.myRetail.controller;

import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.service.MyRetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyRetailControllerTest {

    @InjectMocks
    MyRetailController myRetailController;

    @Mock
    MyRetailService myRetailService;

    @Test
    void testGetProduct_Found() {
        when(myRetailService.getProduct(any(Integer.class))).thenReturn(Optional.of(new ProductResponse()));
        ResponseEntity<ProductResponse> response = myRetailController.getProduct(any(Integer.class));
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    void testGetProduct_NotFound() {
        when(myRetailService.getProduct(any(Integer.class))).thenReturn(Optional.empty());
        ResponseEntity<ProductResponse> response = myRetailController.getProduct(any(Integer.class));
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }





}
