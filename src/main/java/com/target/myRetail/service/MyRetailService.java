package com.target.myRetail.service;

import com.target.myRetail.MyRetailApplication;
import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.integration.service.RedskyService;
import com.target.myRetail.mapper.ProductMapper;
import com.target.myRetail.model.Product;
import com.target.myRetail.repository.MyRetailRepository;
import com.target.myRetail.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Pooja Jayanna
 * @version 1.0
 *
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
    public ProductResponse getProduct(Integer id) throws MyRetailException {
        log.debug("Entering getProduct with id - " + id);
        try {
            Optional<Product> productOptional = myRetailRepository.findById(id);
            if (productOptional.isPresent()) {
                log.debug("Exciting getProduct with ProductResponse - " + productOptional.get().toString());
                CompletableFuture<String> productName = redskyService.getProductName(id);
                return ProductMapper.toProductResponse(productOptional.get(), productName.get()) ;
            } else {
                log.debug("Exciting getProduct with Not found status ");
                throw new MyRetailException(MessageUtils.NOT_FOUND+id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.debug("Exciting getProduct with Exception - " + ex.getMessage());
            log.error("Exciting getProduct with Exception - " + ex.getMessage());
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Makes asynchronous call to db to retrieve product details.
     *
     * @param id
     * @return
     * @throws Exception throws InterruptedException or ExecutionException
     */
    @Async
    private Optional<Product> getProductDetails(Integer id) throws  Exception {
        log.debug("Entering getProductDetails with id - " + id);
        try {
            CompletableFuture<Optional<Product>> completableProduct = CompletableFuture.completedFuture(myRetailRepository.findById(id));
            return completableProduct.get();
        } catch (InterruptedException e) {
            log.debug("Exciting getProductDetails with Exception - " +e.getMessage());
            log.error("Exciting getProductDetails with Exception - " +e.getMessage());
            throw e;
        } catch (ExecutionException e) {
            log.debug("Exciting getProductDetails with Exception - " +e.getMessage());
            log.error("Exciting getProductDetails with Exception - " +e.getMessage());
            throw e;
        }
    }

    /**
     * Updates the product with price
     *
     * @param id
     * @param productRequest
     * @return
     * @throws MyRetailException
     */
    public ProductResponse updateProductPrice(Integer id, ProductRequest productRequest) throws MyRetailException {
        try {
            log.debug("Entering updateProductPrice with ProductRequest - " +productRequest.toString());
            Optional<Product> productOptional = myRetailRepository.findById(id);
            if (productOptional.isPresent()) {
                Product product = myRetailRepository.save(ProductMapper.toProduct(productRequest, id));
                log.debug("Exciting updateProductPrice with Product - " +product.toString());
                return ProductMapper.toProductResponse(product,null);
            } else {
                log.debug("Exciting updateProductPrice with Not Found for id - " +id);
                log.warn("Exciting updateProductPrice with Not Found for id - " +id);
                throw new MyRetailException(MessageUtils.NOT_FOUND+id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.debug("Exciting updateProductPrice with Exception - " +ex.getMessage());
            log.error("Exciting updateProductPrice with Exception - " +ex.getMessage());
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * adds the product details in db
     *
     * @param productRequest
     * @return
     * @throws MyRetailException
     */
    public ProductResponse addProduct(ProductRequest productRequest) throws MyRetailException {
        try {
            log.debug("Entering addProduct with ProductRequest - " + productRequest.toString());
            Product product = myRetailRepository.save(ProductMapper.toProduct(productRequest, null));
            log.debug("Exiting addProduct with Product - " + product.toString());
            return ProductMapper.toProductResponse(product, null);
        } catch (Exception ex) {
            log.debug("Exiting addProduct with Exception - " + ex.getMessage());
            log.error("Exiting addProduct with Exception - " + ex.getMessage());
            throw new MyRetailException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
