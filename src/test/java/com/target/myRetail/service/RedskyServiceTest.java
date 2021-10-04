package com.target.myRetail.service;

import com.target.myRetail.integration.dto.Item;
import com.target.myRetail.integration.dto.Product;
import com.target.myRetail.integration.dto.ProductDescription;
import com.target.myRetail.dto.ProductDetails;
import com.target.myRetail.exception.MyRetailException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.target.myRetail.integration.service.RedskyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RedskyServiceTest {

    @InjectMocks
    RedskyService redskyService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    ResponseEntity<Object> responseEntity;

    @Test
    public void getProductName_Found() {
        ProductDetails productDetail =new ProductDetails();
        Item item = new Item();
        ProductDescription productDescription = new ProductDescription();
        productDescription.setTitle("The Big Lebowski (Blu-ray) (Widescreen)");
        item.setProductDescription(productDescription);
        Product product = new Product();
        product.setItem(item);
        productDetail.setProduct(product);

        when(restTemplate.getForEntity(any(String.class), any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(productDetail);
        String productName = redskyService.getProductName(12345);
        assertEquals("The Big Lebowski (Blu-ray) (Widescreen)", productName);
    }

    @Test
    public void getProductName_NotFound() {
        when(restTemplate.getForEntity(any(String.class), any())).thenThrow(MyRetailException.class);
        assertThrows(MyRetailException.class,() -> redskyService.getProductName(12345));
    }

}
