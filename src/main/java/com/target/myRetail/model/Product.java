package com.target.myRetail.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private Integer id;

    private String name;

    private BigDecimal currencyValue;

    private String currencyCode;


}
