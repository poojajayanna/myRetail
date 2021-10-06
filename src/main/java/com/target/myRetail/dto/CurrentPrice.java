package com.target.myRetail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * CurrentPrice data.
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentPrice {

    private BigDecimal value;

    @JsonProperty("currency_code")
    private CurrencyCode currencyCode;
}
