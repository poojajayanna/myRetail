package com.target.myRetail.service;

import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.integration.dto.ProductDetails;
import com.target.myRetail.integration.service.RedskyService;
import com.target.myRetail.mapper.ProductMapper;
import com.target.myRetail.model.Product;
import com.target.myRetail.repository.MyRetailRepository;
import com.target.myRetail.util.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Pooja Jayanna
 * @version 1.0
 * <p>
 * Service to add, retrieve and update product in db.
 */
@Service
@Slf4j
public class MyRetailService {

    @Autowired
    MyRetailRepository myRetailRepository;

    @Autowired
    RedskyService redskyService;

    /**
     * Retrieves the product details from db and name from Redsky service asynchronously.
     *
     * @param id - accepts productId
     * @return ProductResponse
     * @throws MyRetailException throws Not_Found or Intenal_Server_Error.
     */
    public ProductResponse getProduct(Integer id) {
        log.debug("Entering getProduct with id - " + id);
        try {
            // make async calls
            CompletableFuture<Optional<Product>> productFuture = getProductDetails(id);
            CompletableFuture<ProductDetails> productDetailsFuture = redskyService.getProductName(id);

            Optional<Product> optionalProduct = productFuture.get();
            if (optionalProduct.isPresent()) {
                log.debug("retrieved Product with id - " + optionalProduct.get().getId());
                String productName = productDetailsFuture.get().getProduct().getItem().getProductDescription().getTitle();
                return ProductMapper.toProductResponse(optionalProduct.get(), productName);
            } else {
                log.debug("Product Not found for id: " + id);
                throw new MyRetailException(MessageConstants.NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }
        } catch (ExecutionException | InterruptedException ex) {
            log.error("Error occurred while async call for product id - " + id, ex);
            if(ex.getCause() instanceof HttpClientErrorException.Gone){
                //Exception when service is down
                throw new MyRetailException(MessageConstants.SERVICE_DOWN, HttpStatus.GONE);
            }
            if(ex.getCause() instanceof IOException){
                //Exception when service not found
                throw new MyRetailException(MessageConstants.SERVICE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Makes asynchronous call to db to retrieve product details.
     *
     * @param id -ProductId
     * @return CompletableFuture<Optional < Product>> - async call
     */
    @Async
    public CompletableFuture<Optional<Product>> getProductDetails(Integer id) {
        log.debug("Entering getProductDetails with id - " + id);
        return CompletableFuture.completedFuture(myRetailRepository.findById(id));
    }

    /**
     * Updates the product with price
     *
     * @param id             - ProductId
     * @param productRequest ProductRequest for update
     * @return ProductResponse
     */
    public ProductResponse updateProductPrice(Integer id, ProductRequest productRequest) {
        log.debug("Entering updateProductPrice with ProductRequest - " + productRequest.toString());
        Optional<Product> productOptional = myRetailRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = myRetailRepository.save(ProductMapper.toProduct(productRequest, id));
            log.debug("Exciting updateProductPrice with Product - " + product);
            return ProductMapper.toProductResponse(product, null);
        } else {
            log.warn("Exciting updateProductPrice with Not Found for id - " + id);
            throw new MyRetailException(MessageConstants.NOT_FOUND + id, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * adds the product details in db
     *
     * @param productRequest - Product Request for adding product
     * @return ProductResponse
     */
    public ProductResponse addProduct(ProductRequest productRequest) {
        log.debug("Entering addProduct with ProductRequest - " + productRequest);
        Product product = myRetailRepository.save(ProductMapper.toProduct(productRequest, null));
        log.debug("Exiting addProduct with Product - " + product);
        return ProductMapper.toProductResponse(product, null);

    }
}
