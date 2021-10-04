package com.target.myRetail.mapper;

import com.target.myRetail.dto.CurrencyCode;
import com.target.myRetail.dto.CurrentPrice;
import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.model.Product;

public class ProductMapper {

    public static ProductResponse toProductResponse(Product product, String productName) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getId());
        productResponse.setName(productName);
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(product.getCurrencyValue());
        currentPrice.setCurrencyCode(CurrencyCode.valueOf(product.getCurrencyCode()));
        productResponse.setCurrentPrice(currentPrice);
        return  productResponse;
    }

    public static Product toProduct(ProductRequest productRequest, Integer id) {
        Product product = new Product();
        product.setName(productRequest.getName());
        if (productRequest.getCurrentPrice() != null) {
            product.setCurrencyCode(productRequest.getCurrentPrice().getCurrencyCode().toString());
            product.setCurrencyValue(productRequest.getCurrentPrice().getValue());
        }
        if (id == null) {
            product.setId(productRequest.getProductId());
        } else {
            product.setId(id);
        }
        return  product;
    }
}
