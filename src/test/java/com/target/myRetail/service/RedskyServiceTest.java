package com.target.myRetail.service;

import com.target.myRetail.dto.Item;
import com.target.myRetail.dto.Product;
import com.target.myRetail.dto.ProductDescription;
import com.target.myRetail.dto.ProductDetails;
import com.target.myRetail.exception.MyRetailException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
public class RedskyServiceTest {

    @InjectMocks
    RedskyService redskyService;

    @Mock
    RestTemplate restTemplate;

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

        ResponseEntity mock = mock(ResponseEntity.class);
        when(restTemplate.getForEntity(any(String.class), any())).thenReturn(mock);
        when(mock.getBody()).thenReturn(productDetail);
        String productName = redskyService.getProductName(12345);
        assertEquals("The Big Lebowski (Blu-ray) (Widescreen)", productName);
    }

    @Test
    public void getProductName_NotFound() {
        ResponseEntity mock = mock(ResponseEntity.class);
        when(restTemplate.getForEntity(any(String.class), any())).thenThrow(MyRetailException.class);
        assertThrows(MyRetailException.class,() -> redskyService.getProductName(12345));
    }

}
