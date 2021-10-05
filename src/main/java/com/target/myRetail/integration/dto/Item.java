package com.target.myRetail.integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Item data from external Redsky service.
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    @JsonProperty("product_description")
    private ProductDescription productDescription;
}
