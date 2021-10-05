package com.target.myRetail.repository;

import com.target.myRetail.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Pooja Jayanna
 * @version 1.0
 */
public interface MyRetailRepository extends MongoRepository<Product, Integer> {
}
