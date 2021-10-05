package com.target.myRetail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * Product request data
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductRequest {

    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "ProductId is mandatory")
    private Integer productId;

    @JsonProperty("current_price")
    private CurrentPrice currentPrice;
}
