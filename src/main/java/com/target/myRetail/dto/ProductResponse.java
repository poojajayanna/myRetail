package com.target.myRetail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Product response data
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductResponse {

    @JsonProperty("id")
    private Integer productId;

    private String name;

    @JsonProperty("current_price")
    private CurrentPrice currentPrice;


}
