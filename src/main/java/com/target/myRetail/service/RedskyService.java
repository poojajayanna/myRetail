package com.target.myRetail.service;

import com.target.myRetail.dto.ProductDetails;
import com.target.myRetail.exception.MyRetailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RedskyService {

    @Autowired
    RestTemplate restTemplate;

    public String getProductName(Integer id) throws MyRetailException {
        ProductDetails productDetail = null;
        ResponseEntity<ProductDetails> responseEntity = null;
        try {
            String url = "https://redsky.target.com/v3/pdp/tcin/"+id+"?&key=candidate";
            responseEntity = restTemplate.getForEntity(url, ProductDetails.class);
            productDetail = responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            throw new MyRetailException(ex.getMessage(), ex.getStatusCode());
        }
        return productDetail.getProduct().getItem().getProductDescription().getTitle();
    }
}
