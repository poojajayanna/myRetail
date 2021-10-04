package com.target.myRetail.service;

import com.target.myRetail.MyRetailApplication;
import com.target.myRetail.dto.CurrentPrice;
import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.exception.MyRetailException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = MyRetailApplication.class)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class MyRetailServiceTest {

    @Autowired
    MyRetailService myRetailService;

    @Test
    @Order(1)
    public void addProduct_Success() {
        ProductRequest productRequest = new ProductRequest();
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode("USD");
        currentPrice.setValue(BigDecimal.valueOf(12.33));
        productRequest.setCurrentPrice(currentPrice);
        productRequest.setProductId(13860428);
        ProductResponse productResponse = myRetailService.addProduct(productRequest);
        assertEquals("USD",productResponse.getCurrentPrice().getCurrencyCode());
        assertEquals(BigDecimal.valueOf(12.33),productResponse.getCurrentPrice().getValue());
        assertNotNull(productResponse.getProductId());
    }

    @Test
    public void addProduct_Exception() {
        assertThrows(MyRetailException.class, () -> myRetailService.addProduct(null));
    }

    @Test
    @Order(2)
    public void getProduct_Found() {
        ProductResponse productResponse = myRetailService.getProduct(13860428);
        assertEquals(13860428, productResponse.getProductId());
        assertEquals("USD",productResponse.getCurrentPrice().getCurrencyCode());
        assertEquals(BigDecimal.valueOf(12.33), productResponse.getCurrentPrice().getValue());
    }

    @Test
    public void getProduct_NotFound() {
         assertThrows(MyRetailException.class, () ->myRetailService.getProduct(1386042));
    }

    @Test
    public void getProduct_Exception() {
        assertThrows(MyRetailException.class, () ->myRetailService.getProduct(null));
    }

    @Test
    @Order(3)
    public void updateProductPrice_OK() {
        ProductRequest productRequest = new ProductRequest();
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode("USD");
        currentPrice.setValue(BigDecimal.valueOf(55.78));
        productRequest.setCurrentPrice(currentPrice);
        ProductResponse productResponse = myRetailService.updateProductPrice(13860428, productRequest);
        assertEquals("USD",productResponse.getCurrentPrice().getCurrencyCode());
        assertEquals(BigDecimal.valueOf(55.78),productResponse.getCurrentPrice().getValue());
        assertNotNull(productResponse.getProductId());
    }

    @Test
    public void updateProductPrice_NotFound() {
        ProductRequest productRequest = new ProductRequest();
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode("USD");
        currentPrice.setValue(BigDecimal.valueOf(5.68));
        productRequest.setCurrentPrice(currentPrice);
        assertThrows(MyRetailException.class, () ->myRetailService.updateProductPrice(1386042, productRequest));
    }

    @Test
    public void updateProductPrice_Exception() {
        assertThrows(MyRetailException.class, () ->myRetailService.updateProductPrice(null, new ProductRequest()));
    }

}