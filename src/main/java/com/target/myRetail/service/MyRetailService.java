package com.target.myRetail.service;

import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.mapper.ProductMapper;
import com.target.myRetail.model.Product;
import com.target.myRetail.repository.MyRetailRepository;
import com.target.myRetail.integration.service.RedskyService;
import com.target.myRetail.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MyRetailService {

    @Autowired
    MyRetailRepository myRetailRepository;

    @Autowired
    RedskyService redskyService;

    public ProductResponse getProduct(Integer id) throws MyRetailException {
        try {
            Optional<Product> productOptional = myRetailRepository.findById(id);
            if (productOptional.isPresent()) {
                return ProductMapper.toProductResponse(productOptional.get(), redskyService.getProductName(id));
            } else {
                throw new MyRetailException(MessageUtils.NOT_FOUND+id, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ProductResponse updateProductPrice(Integer id, ProductRequest productRequest) throws MyRetailException {
        try {
            Optional<Product> productOptional = myRetailRepository.findById(id);
            if (productOptional.isPresent()) {
                Product product = myRetailRepository.save(ProductMapper.toProduct(productRequest, id));
                return ProductMapper.toProductResponse(product,null);
            } else {
                throw new MyRetailException(MessageUtils.NOT_FOUND+id, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ProductResponse addProduct(ProductRequest productRequest) throws MyRetailException {
        try {
            Product product = myRetailRepository.save(ProductMapper.toProduct(productRequest, null));
            return ProductMapper.toProductResponse(product, null);
        } catch (IllegalArgumentException ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
