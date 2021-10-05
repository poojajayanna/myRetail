package com.target.myRetail.integration.service;

import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.integration.dto.ProductDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.CompletableFuture;

/**
 * @author Pooja Jayanna
 * @version 1.0
 * <p>
 * Template to call Redsky service.
 */
@Service
public class RedskyService {

    @Autowired
    RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(RedskyService.class);

    @Value("${redskyBaseURL}")
    private String redskyBaseURL;

    @Value("${redskyQueryParam}")
    private String queryParam;

    /**
     * Makes Async call for external Redsky Service.
     * Retries the external call in case of HttpServerErrorException.
     *
     * @param id - retrieve Product for id
     * @return - Async ProductDetails
     */
    @Async
    @Retryable(value = HttpServerErrorException.class, maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public CompletableFuture<ProductDetails> getProductName(Integer id) {
        logger.debug("Entering getProductName with id - " + id);
        int retryCount = 0;
        try {
            logger.info("Retrying Redsky service - " + retryCount++);
            String uri = UriComponentsBuilder.fromUriString(redskyBaseURL + id)
                    .queryParam("key", queryParam)
                    .build().toUriString();
            ResponseEntity<ProductDetails> responseEntity = restTemplate.getForEntity(uri, ProductDetails.class);
            ProductDetails productDetail = responseEntity.getBody();
            logger.debug("Exiting getProductName with ProductDetails - " + responseEntity.toString());
            return CompletableFuture.completedFuture(productDetail);
        } catch (HttpClientErrorException ex) {
            logger.debug("Exiting getProductName with Exception - " + ex.getMessage());
            logger.error("Exiting getProductName with Exception - " + ex.getMessage());
            throw new MyRetailException(ex.getMessage(), ex.getStatusCode());
        }
    }
}
