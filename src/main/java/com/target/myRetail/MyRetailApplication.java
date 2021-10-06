package com.target.myRetail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Pooja Jayanna
 * @version 1.0
 */
@SpringBootApplication
@EnableAsync
@EnableRetry
@Slf4j
public class MyRetailApplication {

	public static void main(String[] args) {
		log.debug("Entering MyRetailApplication main ");
		SpringApplication.run(MyRetailApplication.class, args);
	}

}
