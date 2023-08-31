package com.dpcode123.products.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/")
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    /**
     * Gets paginated products.
     * Default params endpoint: /api/product/paginated
     * Custom params endpoint: /api/product/paginated?pageSize=10&pageNumber=2&sortDirection=ASC
     * @param productsPage
     * @return
     */
    @GetMapping(path = "/paginated")
    public ResponseEntity<Page<ProductDTO>> findAllPaginated(ProductsPage productsPage) {
        return ResponseEntity.ok(productService.findProductsPaginated(productsPage));
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @GetMapping(path = "/code/{productCode}")
    public ResponseEntity<ProductDTO> findProductByCode(@PathVariable String productCode) {
        return ResponseEntity.ok(productService.findProductByCode(productCode));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.addProduct(productRequest));
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<ProductDTO> editProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.editProduct(productId, productRequest));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

}
