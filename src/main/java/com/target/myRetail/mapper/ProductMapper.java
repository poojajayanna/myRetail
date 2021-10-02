package com.target.myRetail.mapper;

import com.target.myRetail.dto.CurrentPrice;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.model.Product;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getProductId());
        productResponse.setName(product.getName());
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(product.getCurrencyValue());
        currentPrice.setCurrencyCode(product.getCurrencyCode());
        productResponse.setCurrentPrice(currentPrice);
        return  productResponse;
    }
}
