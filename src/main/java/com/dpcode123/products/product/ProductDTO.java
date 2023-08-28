package com.dpcode123.products.product;

import java.math.BigDecimal;

public record ProductDTO(Long id,
                         String code,
                         String name,
                         String description,
                         Boolean isAvailable,
                         BigDecimal priceEur,
                         BigDecimal priceUsd) {
}
