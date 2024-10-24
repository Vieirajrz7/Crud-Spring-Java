package com.example.crud.exceptions;

public class ProductNotFoundExceptions extends RuntimeException {
    public ProductNotFoundExceptions(String message) {
        super(message);
    }
}
