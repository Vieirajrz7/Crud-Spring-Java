package com.example.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.crud.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    boolean existsByName(String name);
    Optional<ProductModel> findByName(String name);

    // Query Personalizada para listar em ordem crescente
    @Query("SELECT p FROM productModel p ORDER BY p.id ASC")
    List<ProductModel> findAllOrderedById();
}
