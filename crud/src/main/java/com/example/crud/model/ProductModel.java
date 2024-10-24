package com.example.crud.model;

import com.example.crud.dto.ProductDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name="productModel")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductModel {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Integer price_in_cents;

    public ProductModel(ProductDTO data) {
        this.name = data.name();
        this.price_in_cents = data.price_in_cents();
    }

    public ProductModel(String name, int price) {
        this.name = name;
        this.price_in_cents = price;
    }
}
