package com.target.myRetail.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Item {

    @JsonProperty("product_description")
    private ProductDescription productDescription;
}
