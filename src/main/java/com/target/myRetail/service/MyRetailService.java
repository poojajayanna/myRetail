package com.target.myRetail.service;

import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.mapper.ProductMapper;
import com.target.myRetail.model.Product;
import com.target.myRetail.repository.MyRetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MyRetailService {

    @Autowired
    MyRetailRepository myRetailRepository;

    public Optional<ProductResponse> getProduct(Integer id) {
        Optional<Product> productOptional= myRetailRepository.findById(id);
        if(productOptional.isPresent()) {
            return Optional.of(ProductMapper.toProductResponse(productOptional.get()));
        }
        return null;
    }

    public Optional<ProductResponse> updateProductPrice(Integer id, ProductRequest productRequest) {
        return null;
    }
}
