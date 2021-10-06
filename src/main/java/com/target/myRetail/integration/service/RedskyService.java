package com.target.myRetail.integration.service;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author Pooja Jayanna
 * @version 1.0
 *
 * Template to call Redsky service.
 */
@Service
public class RedskyService {

    @Autowired
    RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(RedskyService.class);

    @Value("${redsky.base.url}")
    private String redskyBaseURL;

    @Value("${redsky.api.key}")
    private String apiKey;

    /**
     * Makes Async call for external Redsky Service.
     * Retries the external call in case of HttpServerErrorException.
     *
     * @param id - retrieve Product for id
     * @return - Async ProductDetails
     */
    @Async
    @Retryable(value = IOException.class, maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public CompletableFuture<ProductDetails> getProductName(Integer id)  {
        logger.debug("Entering getProductName with id - " + id);
        String uri = UriComponentsBuilder.fromUriString(redskyBaseURL + id)
                .queryParam("key", apiKey)
                .build().toUriString();
        ResponseEntity<ProductDetails> responseEntity = restTemplate.getForEntity(uri, ProductDetails.class);
        ProductDetails productDetail = responseEntity.getBody();
        logger.debug("Exiting getProductName with ProductDetails - " + productDetail);
        return CompletableFuture.completedFuture(productDetail);

    }
}
