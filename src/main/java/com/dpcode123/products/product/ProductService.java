package com.dpcode123.products.product;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    ProductDTO findProductById(Long productId);

    ProductDTO findProductByCode(String productCode);

    ProductDTO addProduct(ProductRequest productRequest);

    ProductDTO editProduct(Long productId, ProductRequest productRequest);

    Long deleteProduct(Long productId);

}
