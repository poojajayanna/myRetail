package com.target.myRetail.service;

import com.target.myRetail.dto.Product;
import com.target.myRetail.dto.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RedskyService {

    @Autowired
    RestTemplate restTemplate;

    public String getProductName(Integer id) {
        ProductDetails productDetail = null;
        String url = "https://redsky.target.com/v3/pdp/tcin/"+id+"?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";
        ResponseEntity<ProductDetails>  responseEntity = restTemplate.getForEntity(url, ProductDetails.class);
        Optional<ResponseEntity<ProductDetails>> optionalResponseEntity = Optional.of(responseEntity);
        if (optionalResponseEntity.isPresent()) {
            productDetail = optionalResponseEntity.get().getBody();
        }
        return productDetail.getProduct().getItem().getProductDescription().getTitle();
    }
}
