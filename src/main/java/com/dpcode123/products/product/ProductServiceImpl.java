package com.dpcode123.products.product;

import com.dpcode123.products.codegenerator.CodeGeneratorService;
import com.dpcode123.products.currency.Currency;
import com.dpcode123.products.currency.converter.CurrencyConverterService;
import com.dpcode123.products.exception.exceptions.NoContentFoundException;
import com.dpcode123.products.exception.exceptions.TransactionFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CurrencyConverterService currencyConverterService;
    private final CodeGeneratorService codeGeneratorService;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapProductToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findProductsPaginated(ProductsPage productsPage) {
        return productRepository.findAll(productsPage.createPageable())
                .map(this::mapProductToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findProductById(Long productId) {
        return productRepository.findById(productId)
                .map(this::mapProductToDTO)
                .orElseThrow(() -> new NoContentFoundException("Product not found by id: " + productId));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findProductByCode(String productCode) {
        return productRepository.findByCode(productCode)
                .map(this::mapProductToDTO)
                .orElseThrow(() -> new NoContentFoundException("Product not found by code: " + productCode));
    }

    @Override
    @Transactional
    public ProductDTO addProduct(ProductRequest productRequest) {
        Product product = mapCommandToProduct(productRequest);
        product.setCode(codeGeneratorService.generateUniqueProductCode());

        ProductDTO addedProduct = Optional.of(productRepository.save(product))
                .map(this::mapProductToDTO)
                .orElseThrow(() -> new TransactionFailedException("Product not added."));

        log.info("Product {} added", addedProduct.id());
        return addedProduct;
    }

    @Override
    @Transactional
    public ProductDTO editProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoContentFoundException("Product edit failed. Not found, id: " + productId));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice_eur(productRequest.getPriceEur());
        product.setIs_available(productRequest.getIsAvailable());

        ProductDTO productDTO = Optional.of(productRepository.save(product))
                .map(this::mapProductToDTO)
                .orElseThrow(() -> new TransactionFailedException("Product edit failed, id: " + productId));

        log.info("Product {} edited", productId);
        return productDTO;
    }

    @Override
    @Transactional
    public Long deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new NoContentFoundException("Product not deleted. Not found, id: " + productId);
        }

        Long deletedProducts = productRepository.removeById(productId);
        if (deletedProducts.equals(0L)) {
            throw new TransactionFailedException("Product not deleted, id: + " + productId);
        }

        log.info("Product {} deleted", productId);
        return deletedProducts;
    }

    private ProductDTO mapProductToDTO(Product product) {
        BigDecimal priceUsd = currencyConverterService.convertEurTo(Currency.USD, product.getPrice_eur());
        return new ProductDTO(product.getId(), product.getCode(), product.getName(), product.getDescription(), product.getIs_available(), product.getPrice_eur(), priceUsd);
    }

    private Product mapCommandToProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice_eur(productRequest.getPriceEur());
        product.setDescription(productRequest.getDescription());
        product.setIs_available(productRequest.getIsAvailable());
        return product;
    }
}
