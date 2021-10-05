package com.target.myRetail.integration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Product data from external Redsky service.
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Item item;
}
