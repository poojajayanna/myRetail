package com.target.myRetail.controller;

import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.exception.MyRetailException;
import com.target.myRetail.service.MyRetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/products")
public class MyRetailController {

    @Autowired
    MyRetailService myRetailService;

    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest ) throws MyRetailException
    {
        ProductResponse productResponse = myRetailService.addProduct(productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable @NotNull Integer id ) throws MyRetailException
    {
        ProductResponse productResponse = myRetailService.getProduct(id);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);

    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> updateProductPrice(@PathVariable Integer id, @RequestBody ProductRequest productRequest )
    {
        ProductResponse productResponse = myRetailService.updateProductPrice(id, productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


}
