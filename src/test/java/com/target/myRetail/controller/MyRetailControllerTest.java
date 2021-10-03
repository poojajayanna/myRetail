package com.target.myRetail.controller;

import com.target.myRetail.dto.CurrentPrice;
import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.service.MyRetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
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
        when(myRetailService.getProduct(any(Integer.class))).thenReturn(new ProductResponse());
        ResponseEntity<ProductResponse> response = myRetailController.getProduct(any(Integer.class));
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    void testGetProduct_NotFound() {
        when(myRetailService.getProduct(any(Integer.class))).thenThrow(MyRetailException.class);
        ResponseEntity<ProductResponse> response = myRetailController.getProduct(any(Integer.class));
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateProductPrice_Updated() {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("productPrice");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode("USD");
        currentPrice.setValue(BigDecimal.valueOf(12.12));
        productRequest.setCurrentPrice(currentPrice);
        when(myRetailService.updateProductPrice(any(Integer.class),any(ProductRequest.class))).thenReturn(Optional.of(new ProductResponse()));
        ResponseEntity<ProductResponse> response = myRetailController.updateProductPrice(123456,productRequest);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateProductPrice_NotFound() {
        when(myRetailService.updateProductPrice(any(Integer.class),any(ProductRequest.class))).thenReturn(Optional.empty());
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("productPrice");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode("USD");
        currentPrice.setValue(BigDecimal.valueOf(12.12));
        productRequest.setCurrentPrice(currentPrice);
        ResponseEntity<ProductResponse> response = myRetailController.updateProductPrice(123456,productRequest);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }





}
