package com.target.myRetail.integration.service;

import com.target.myRetail.integration.dto.ProductDetails;
import com.target.myRetail.exception.MyRetailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Pooja Jayanna
 * @version 1.0
 *
 * Template to call Redsky service.
 */
@Service
public class RedskyService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${redskyBaseURL}")
    private String redskyBaseURL;

    @Value("${redskyExclusionURL}")
    private String redskyExclusionURL;

    Logger logger = LoggerFactory.getLogger(RedskyService.class);

    /**
     * Read product name from the Redsky service for product Id.
     *
     * @param id
     * @return
     * @throws MyRetailException
     */
    @Async
    public CompletableFuture<String> getProductName(Integer id) throws Exception {
        logger.debug("Entering getProductName with id - " + id);
        try {
            String url = redskyBaseURL + id + redskyExclusionURL;
            ResponseEntity<ProductDetails> responseEntity = restTemplate.getForEntity(url, ProductDetails.class);
            ProductDetails productDetail = responseEntity.getBody();
            logger.debug("Exiting getProductName with ProductDetails - " + productDetail.toString());
            return CompletableFuture.completedFuture(productDetail.getProduct().getItem().getProductDescription().getTitle());
        } catch (HttpClientErrorException ex) {
            logger.debug("Exiting getProductName with HttpClientErrorException - " + ex.getMessage());
            logger.error("Exiting getProductName with HttpClientErrorException - " + ex.getMessage());
            throw new MyRetailException(ex.getMessage(), ex.getStatusCode());
        }
    }
}
