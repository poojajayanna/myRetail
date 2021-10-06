package com.target.myRetail.integration.service;

import com.target.myRetail.MyRetailApplication;
import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.integration.dto.ProductDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Pooja Jayanna
 * @version 1.0
 *
 * Integration - Test for Redsky service.
 */
@SpringBootTest(classes = MyRetailApplication.class)
@TestPropertySource(
        locations = "classpath:application-test.properties")

public class RedskyServiceIntegrationTest {

    @Autowired
    RedskyService redskyService;

    @Test
    public void testGetProductName_Found() throws Exception {

        ProductDetails productDetails = redskyService.getProductName(13860428).get();
        assertNotNull(productDetails);
        assertEquals("The Big Lebowski (Blu-ray)", productDetails.getProduct().getItem().getProductDescription().getTitle());
    }

   @Test
    public void testGetProductName_NotFound() {
        try {
            redskyService.getProductName(12345).get();
        } catch (Exception e) {
            assertTrue(e instanceof ExecutionException);
            assertTrue(e.getCause() instanceof MyRetailException);
            assertTrue(e.getLocalizedMessage().contains("NOT_FOUND"));
        }
    }
}
