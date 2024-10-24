package com.example.crud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductExceptions.class)
    public ResponseEntity<String> handleProductNotCreatedException(ProductExceptions ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductDeletedException.class)
    public ResponseEntity<String> handleProductDeletedException() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ProductNotFoundExceptions.class)
    public ResponseEntity<String> handleProductNotFoundExceptions(ProductNotFoundExceptions ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductCreateExceptions.class)
    public ResponseEntity<String> handleProductCreateExceptions() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}