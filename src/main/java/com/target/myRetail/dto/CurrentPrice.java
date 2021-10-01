package com.target.myRetail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CurrentPrice {

    private BigDecimal value;

    @JsonProperty("currency_code")
    private String currencyCode;
}
