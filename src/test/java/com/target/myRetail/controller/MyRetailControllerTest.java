package com.target.myRetail.controller;

import com.target.myRetail.dto.CurrencyCode;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * @author Pooja Jayanna
 * @version 1.0
 *
 * Unit - Test for controller.
 */
@ExtendWith(MockitoExtension.class)
public class MyRetailControllerTest {

    @InjectMocks
    MyRetailController myRetailController;

    @Mock
    MyRetailService myRetailService;

    @Test
    void testAddProduct_Created() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("productPrice");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode(CurrencyCode.valueOf("USD"));
        currentPrice.setValue(BigDecimal.valueOf(12.12));
        productRequest.setProductId(13860428);
        productRequest.setCurrentPrice(currentPrice);
        when(myRetailService.addProduct(any(ProductRequest.class))).thenReturn(new ProductResponse());
        ResponseEntity<ProductResponse> response = myRetailController.addProduct(productRequest);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAddProduct_Exception() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("productPrice");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode(CurrencyCode.valueOf("USD"));
        currentPrice.setValue(BigDecimal.valueOf(12.12));
        productRequest.setProductId(13860428);
        productRequest.setCurrentPrice(currentPrice);
        when(myRetailService.addProduct(any(ProductRequest.class))).thenThrow(MyRetailException.class);
        assertThrows(MyRetailException.class, () -> myRetailController.addProduct(productRequest));
    }

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
        assertThrows(MyRetailException.class, () -> myRetailController.getProduct(any(Integer.class)));
    }

    @Test
    void testUpdateProductPrice_Updated() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("productPrice");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode(CurrencyCode.valueOf("USD"));
        currentPrice.setValue(BigDecimal.valueOf(12.12));
        productRequest.setCurrentPrice(currentPrice);
        when(myRetailService.updateProductPrice(any(Integer.class),any(ProductRequest.class))).thenReturn(new ProductResponse());
        ResponseEntity<ProductResponse> response = myRetailController.updateProductPrice(123456,productRequest);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateProductPrice_NotFound() {
        when(myRetailService.updateProductPrice(any(Integer.class),any(ProductRequest.class))).thenThrow(MyRetailException.class);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("productPrice");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode(CurrencyCode.valueOf("USD"));
        currentPrice.setValue(BigDecimal.valueOf(12.12));
        productRequest.setCurrentPrice(currentPrice);
        assertThrows(MyRetailException.class, () -> myRetailController.updateProductPrice(123456,productRequest));
    }

}
