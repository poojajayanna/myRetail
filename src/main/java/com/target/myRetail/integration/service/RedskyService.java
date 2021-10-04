package com.target.myRetail.integration.service;

import com.target.myRetail.dto.ProductDetails;
import com.target.myRetail.exception.MyRetailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RedskyService {

    @Autowired
    RestTemplate restTemplate;

    public String getProductName(Integer id) throws MyRetailException {
        ProductDetails productDetail;
        ResponseEntity<ProductDetails> responseEntity;
        try {
            String url = "https://redsky.target.com/v3/pdp/tcin/"+id+"?&key=candidate";
            responseEntity = restTemplate.getForEntity(url, ProductDetails.class);
            productDetail = responseEntity.getBody();
            return productDetail.getProduct().getItem().getProductDescription().getTitle();
        } catch (HttpClientErrorException ex) {
            throw new MyRetailException(ex.getMessage(), ex.getStatusCode());
        } catch (Exception ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
