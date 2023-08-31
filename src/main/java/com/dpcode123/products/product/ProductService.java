package com.dpcode123.products.product;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    Page<ProductDTO> findProductsPaginated(ProductsPage productsPage);

    ProductDTO findProductById(Long productId);

    ProductDTO findProductByCode(String productCode);

    ProductDTO addProduct(ProductRequest productRequest);

    ProductDTO editProduct(Long productId, ProductRequest productRequest);

    Long deleteProduct(Long productId);

}
