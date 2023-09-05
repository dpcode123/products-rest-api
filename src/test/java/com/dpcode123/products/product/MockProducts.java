package com.dpcode123.products.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockProducts {

    public static ProductRequest getMockProductRequest() {
        return new ProductRequest("Product name", BigDecimal.valueOf(123), "Product description", true);
    }

    public static List<Product> getMockProducts(int numberOfProducts) {
        List<Product> mockProducts = new ArrayList<>();
        for (int i = 0; i < numberOfProducts; i++) {
            mockProducts.add(getMockProductWithId((long) i));
        }
        return mockProducts;
    }

    public static Product getMockProductWithId(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Product Name " + id);
        product.setCode("ABCDE12345");
        product.setDescription("Description " + id);
        product.setPrice_eur(BigDecimal.valueOf(123L));
        product.setIs_available(true);
        return product;
    }

    public static List<ProductDTO> getMockProductDTOs(int numberOfProductDTOs) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = getMockProducts(numberOfProductDTOs);

        for (Product p : products) {
            productDTOS.add(new ProductDTO(p.getId(), p.getCode(), p.getName(), p.getDescription(), p.getIs_available(), p.getPrice_eur(), BigDecimal.ONE));
        }
        return productDTOS;
    }

    public static Product getMockProductFromProductRequest(ProductRequest productRequest) {
        Product product = new Product();
        product.setId(1L);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice_eur(productRequest.getPriceEur());
        product.setIs_available(productRequest.getIsAvailable());
        return product;
    }

    public static Product getMockProduct() {
        return getMockProducts(1).get(0);
    }

    public static ProductDTO getMockProductDTO() {
        return getMockProductDTOs(1).get(0);
    }
}
