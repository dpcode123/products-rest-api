package com.dpcode123.products.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "price_eur")
    private BigDecimal price_eur;

    @Column(name = "description")
    private String description;

    @Column(name = "is_available")
    private Boolean is_available;
}
