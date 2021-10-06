package com.target.myRetail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author Pooja Jayanna
 * @version 1.0
 *
 * Application configuration.
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${redsky.connectTimeout}")
    private long connectTimeOut;

    @Value("${redsky.readTimeout}")
    private long readTimeout;


    /**
     * Creating rest template bean.
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(connectTimeOut))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
