package com.target.myRetail.repository;

import com.target.myRetail.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyRetailRepository extends MongoRepository<Product, Integer> {
}
