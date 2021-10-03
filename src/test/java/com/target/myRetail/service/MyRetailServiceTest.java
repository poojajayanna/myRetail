package com.target.myRetail.service;

import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.repository.MyRetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@DataMongoTest
public class MyRetailServiceTest {

    @TestConfiguration
    static class MyRetailServiceTestContextConfiguration {

        @Bean
        public MyRetailService myRetailService() {
            return new MyRetailService();
        }

    }
    @Autowired
    MyRetailService myRetailService;

    @Autowired
    MyRetailRepository myRetailRepository;


    @Test
    public void getProduct_Found() {
        Assertions.assertThrows(MyRetailException.class, ()-> myRetailService.getProduct(13860428));
    }
}
