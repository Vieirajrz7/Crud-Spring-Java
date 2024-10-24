package com.example.crud;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.crud.dto.ProductDTO;
import com.example.crud.exceptions.ProductDeletedException;
import com.example.crud.exceptions.ProductExceptions;
import com.example.crud.model.ProductModel;
import com.example.crud.repository.ProductRepository;
import com.example.crud.service.ProductService;

public class testMethodsProduct {

    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMethodCreateProduct() throws Exception {

        when(service.productExist("Mouse")).thenReturn(false);

        ProductDTO product = new ProductDTO("Mouse", 7000);
        ProductModel newProduct = new ProductModel(product);

        when(repository.save(any(ProductModel.class))).thenReturn(newProduct);

        ProductModel createdProduct = service.createNewProduct(product);

        assertNotNull(createdProduct);
        assertEquals("Mouse", createdProduct.getName());
        assertEquals(7000, createdProduct.getPrice_in_cents());

        verify(repository).save(any(ProductModel.class));
    }

    @Test
    public void testMethodCreateProductExisting() throws Exception {

        when(service.productExist("Mouse")).thenReturn(true);

        ProductDTO newProduct = new ProductDTO("Mouse", 7000);

        Exception exception = assertThrows(Exception.class, () -> {
            service.createNewProduct(newProduct);
        });

        assertEquals("Já Existe um produto cadastrado com este nome!", exception.getMessage());
        verify(repository, never()).save(any(ProductModel.class));

    }

    @Test
    public void testMethodSearchProductByName() throws Exception {
        
        ProductModel searchProduct = new ProductModel();
        searchProduct.setName("Boné");

        when(repository.findByName("Boné")).thenReturn(Optional.of(searchProduct));

        Optional<ProductModel> foundProduct = service.searchProductByName("Boné");

        assertTrue(foundProduct.isPresent());
        assertEquals("Boné", foundProduct.get().getName());
        verify(repository, times(1)).findByName("Boné");
    }

    @Test
    public void testMethodSearchProductByNameNotFound() throws Exception {

        when(repository.findByName("Boné")).thenReturn(Optional.empty());

        ProductExceptions exception = assertThrows(ProductExceptions.class, () -> {
            service.searchProductByName("Boné");
        });

        assertEquals("Não existe nenhum produto com o nome: Boné", exception.getMessage());
        verify(repository, times(1)).findByName("Boné");
    }

    @Test
    public void testMethodSearchProductById() throws Exception {

        ProductModel product = new ProductModel("Camisa", 4000);
        product.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(product));

        Optional<ProductModel> foundProduct = service.searchProductById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals(1L, foundProduct.get().getId());
        verify(repository, times(1)).findById(1L);
    }
    // To do By Id not found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

    @Test
    public void testMethodUpdateProduct() throws Exception {
        
        ProductModel productExisting = new ProductModel("Camisa", 5000);
        productExisting.setId(1L);

        // Simula a existencia de um produto
        when(repository.findById(1L)).thenReturn(Optional.of(productExisting));

        ProductDTO productUpdatedDTO = new ProductDTO("Camisa", 4000);

        // Simular salvar o produto atualizado
        when(repository.save(any(ProductModel.class))).thenReturn(productExisting);

        Optional<ProductModel> updatedProduct = service.updateProduct(1L ,productUpdatedDTO);

        assertTrue(updatedProduct.isPresent());
        assertEquals(4000, updatedProduct.get().getPrice_in_cents());
        assertEquals("Camisa", updatedProduct.get().getName());
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        ProductDTO productUpdatedDTO = new ProductDTO("Camisa", 4000);
            
        // Simula que o produto não existe
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductExceptions.class, () -> {
            service.updateProduct(1L, productUpdatedDTO);
        });

        assertEquals("Não encontramos o produto com o id: 1 para atualizar", exception.getMessage());
    }

    @Test
    public void testUpdateProductWithInvalidData() throws Exception {
        ProductModel productExisting = new ProductModel("Camisa", 5000);
        productExisting.setId(1L);

        // Simula a existencia de um produto
        when(repository.findById(1L)).thenReturn(Optional.of(productExisting));

        ProductDTO productUpdatedDTO = new ProductDTO("", null); // Dados inválidos

        Exception exception = assertThrows(ProductExceptions.class, () -> {
            service.updateProduct(1L, productUpdatedDTO);
        });

        assertEquals("Não foi possível atualizar o produto. Faltam dados obrigatórios", exception.getMessage());
    }


    // @Test
    // public void testMethodDeleteProduct() throws Exception {

    //     ProductModel productExisting = new ProductModel("Camisa", 5000);
    //     productExisting.setId(1L);

    //     // Simula a existencia de um produto
    //     when(repository.findById(1L)).thenReturn(Optional.of(productExisting));

    //     doNothing().when(repository).deleteById(1L);

    //     service.deleteProductById(1L);

    //     verify(repository, times(1)).deleteById(1L);
    // }


}
