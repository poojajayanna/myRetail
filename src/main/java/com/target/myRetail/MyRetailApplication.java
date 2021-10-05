package com.target.myRetail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * @author Pooja Jayanna
 * @version 1.0
 */
@SpringBootApplication
@EnableRetry
@EnableAsync
@Slf4j
public class MyRetailApplication {

	public static void main(String[] args) {
		log.debug("Entering MyRetailApplication main ");
		SpringApplication.run(MyRetailApplication.class, args);
	}

	/**
	 * Creating rest template bean.
	 *
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
