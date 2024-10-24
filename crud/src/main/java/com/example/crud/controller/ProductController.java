package com.example.crud.controller;

import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.crud.dto.ProductDTO;
import com.example.crud.model.ProductModel;
import com.example.crud.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;
    
    @GetMapping
    public ResponseEntity getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    // Search Product by Name
    @GetMapping("/{name}")
    public ResponseEntity searchProductByName(@PathVariable String name) {

        Optional<ProductModel> foundProduct = service.searchProductByName(name);

        return ResponseEntity.of(foundProduct);

    }

    // Search Product by ID
    @GetMapping("/search-by/{id}")
    public ResponseEntity searchProductById(@PathVariable Long id) {

        Optional<ProductModel> foundProduct = service.searchProductById(id);

        return ResponseEntity.of(foundProduct);
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody @Valid ProductDTO data) throws Exception {

        ProductModel newProduct = service.createNewProduct(data);
        // return ResponseEntity.ok(newProduct).status(HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProductById(@PathVariable Long id, @RequestBody @Valid ProductDTO data) throws Exception {

        Optional<ProductModel> updateProduct = service.updateProduct(id, data);

        return ResponseEntity.of(updateProduct);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProductById(@PathVariable Long id) throws Exception {
        
        return (ResponseEntity) ResponseEntity.of(service.deleteProductById(id));
    }

}
