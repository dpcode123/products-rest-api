package com.dpcode123.products.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Sql("/insert-product.sql")
    void findByCode() {
        String productCode = "ABCDE12345";
        Optional<Product> product = productRepository.findByCode(productCode);

        assertThat(product).isPresent();
        assertThat(product.get().getCode().equals(productCode)).isTrue();
    }

    @Test
    @Sql("/insert-product.sql")
    void findByCode_ProductNotFound() {
        String productCode = "xxxxxxxxxx";
        Optional<Product> product = productRepository.findByCode(productCode);

        assertThat(product).isEmpty();
    }

    @Test
    @Sql("/insert-product.sql")
    void existsByCode() {
        String productCode = "ABCDE12345";
        boolean productExists = productRepository.existsByCode(productCode);

        assertThat(productExists).isTrue();
    }

    @Test
    @Sql("/insert-product.sql")
    void existsByCode_ProductNotFound() {
        String productCode = "xxxxxxxxxx";
        boolean productExists = productRepository.existsByCode(productCode);

        assertThat(productExists).isFalse();
    }

    @Test
    @Sql("/insert-product.sql")
    void removeById() {
        Long productId = 1L;
        Long removedProducts = productRepository.removeById(productId);

        assertThat(removedProducts.equals(1L)).isTrue();
    }

    @Test
    @Sql("/insert-product.sql")
    void removeById_ProductNotFound() {
        Long productId = 999999999999999999L;
        Long removedProducts = productRepository.removeById(productId);
        System.out.println(removedProducts);

        assertThat(removedProducts.equals(0L)).isTrue();
    }
}