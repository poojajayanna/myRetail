package com.target.myRetail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductRequest {

    private String name;

    private Integer productId;

    @JsonProperty("current_price")
    private CurrentPrice currentPrice;
}
