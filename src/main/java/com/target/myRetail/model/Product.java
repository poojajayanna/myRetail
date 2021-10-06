package com.target.myRetail.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Product document
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@Data
@Document(collection = "products")
public class Product {

    @Id
    private Integer id;

    private String name;

    private BigDecimal price;

    private String currencyCode;


}
