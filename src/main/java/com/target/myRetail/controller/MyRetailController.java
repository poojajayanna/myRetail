package com.target.myRetail.controller;

import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.service.MyRetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class MyRetailController {

    @Autowired
    MyRetailService myRetailService;

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Integer id )
    {
        Optional<ProductResponse> optionalProductResponse = myRetailService.getProduct(id);

        return optionalProductResponse.map(productResponse -> new ResponseEntity<>(productResponse, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> updateProductPrice(@PathVariable Integer id, @RequestBody ProductRequest productRequest )
    {
        Optional<ProductResponse> optionalProductResponse = myRetailService.updateProductPrice(id, productRequest);
        return optionalProductResponse.map(productResponse -> new ResponseEntity<>(productResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
