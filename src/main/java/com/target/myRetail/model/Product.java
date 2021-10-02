package com.target.myRetail.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private Integer id;

    private Integer productId;

    private String name;

    private BigDecimal currencyValue;

    private String currencyCode;


}
