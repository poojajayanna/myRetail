package com.target.myRetail.mapper;

import com.target.myRetail.dto.CurrencyCode;
import com.target.myRetail.dto.CurrentPrice;
import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.model.Product;
import lombok.extern.slf4j.Slf4j;

/**
 * Mapper to convert data to model and vice versa
 *
 * @author Pooja Jayanna
 * @version 1.0
 */
@Slf4j
public class ProductMapper {
    /**
     * Convert Product Model Object to ProductResponse Object.
     *
     * @param product     - Model Object
     * @param productName - ProductName from External Service.
     * @return ProductResponse
     */
    public static ProductResponse toProductResponse(Product product, String productName) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getId());
        productResponse.setName(productName);
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(product.getPrice());
        currentPrice.setCurrencyCode(CurrencyCode.valueOf(product.getCurrencyCode()));
        productResponse.setCurrentPrice(currentPrice);
        return productResponse;
    }

    /**
     * Convert ProductRequest Object to Product Model Object.
     *
     * @param productRequest - Request Object.
     * @param id             - productId.
     * @return Product
     */
    public static Product toProduct(ProductRequest productRequest, Integer id) {
        Product product = new Product();
        product.setName(productRequest.getName());
        if (productRequest.getCurrentPrice() != null) {
            CurrentPrice currentPrice = productRequest.getCurrentPrice();
            /* setting USD as default currency code */
            CurrencyCode currencyCode = currentPrice.getCurrencyCode() == null ? CurrencyCode.USD : currentPrice.getCurrencyCode();
            product.setCurrencyCode(currencyCode.toString());
            product.setPrice(currentPrice.getValue());
        }
        if (id == null) {
            product.setId(productRequest.getProductId());
        } else {
            product.setId(id);
        }
        return product;
    }
}
