package com.target.myRetail.controller;

import com.target.myRetail.dto.ProductRequest;
import com.target.myRetail.dto.ProductResponse;
import com.target.myRetail.service.MyRetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Pooja Jayanna
 * @version 1.0
 *
 * Controller class for MyRetail service.
 *
 */
@RestController
@Slf4j
@RequestMapping(path = "/products")
public class MyRetailController {

    @Autowired
    MyRetailService myRetailService;

    /**
     * Adds product details in db.
     *
     * @param productRequest Request for adding Product
     * @return returns ProductResponse with status CREATED
     */
    @PostMapping(path="/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest productRequest )
    {
        log.debug("Entering addProduct with ProductRequest - " + productRequest.toString());
        ProductResponse productResponse = myRetailService.addProduct(productRequest);
        log.debug("Exiting addProduct with ProductResponse - " + productResponse.toString());
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve product form db and product name from Redsky service.
     *
     * @param id - Retrieve Pruct for id
     * @return - ProductResponse with status FOUND
     */
    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable @NotNull Integer id )
    {
        log.debug("Entering getProduct with id - " + id);
        ProductResponse productResponse = myRetailService.getProduct(id);
        log.debug("Exiting getProduct with ProductResponse - " + productResponse.toString());
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);

    }

    /**
     * Updates the price for given product id.
     *
     * @param id - Update price for id
     * @param productRequest - Price deatisl to be updated
     * @return ProductResponse - updated price with status OK
     */
    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> updateProductPrice(@NotNull @PathVariable Integer id,@Valid @RequestBody ProductRequest productRequest )
    {
        log.debug("Entering updateProductPrice with id - " + id +"and ProductRequest - " + productRequest.toString());
        ProductResponse productResponse = myRetailService.updateProductPrice(id, productRequest);
        log.debug("Exiting updateProductPrice with ProductResponse - " + productResponse.toString());
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


}
