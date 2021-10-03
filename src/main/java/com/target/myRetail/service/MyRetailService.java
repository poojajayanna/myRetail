package com.target.myRetail.service;

import com.target.myRetail.dto.ProductDetails;
import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.mapper.ProductMapper;
import com.target.myRetail.model.Product;
import com.target.myRetail.repository.MyRetailRepository;
import com.target.myRetail.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MyRetailService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MyRetailRepository myRetailRepository;

    public ProductResponse getProduct(Integer id) throws MyRetailException {
        Optional<Product> productOptional;
        try {
            productOptional = myRetailRepository.findById(id);
            if (productOptional.isPresent()) {
                return ProductMapper.toProductResponse(productOptional.get(), getProductName(id));
            } else {
                throw new MyRetailException(MessageUtils.NOT_FOUND+id, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public Optional<ProductResponse> updateProductPrice(Integer id, ProductRequest productRequest) {
        return null;
    }

    private String getProductName(Integer id) throws MyRetailException {
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
