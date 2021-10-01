package com.target.myRetail.service;

import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyRetailService {
    public Optional<ProductResponse> getProduct(Integer id) {
        return null;
    }

    public Optional<ProductResponse> updateProductPrice(Integer id, ProductRequest productRequest) {
        return null;
    }
}
