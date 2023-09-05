package com.dpcode123.products.product;

import com.dpcode123.products.codegenerator.CodeGeneratorService;
import com.dpcode123.products.currency.converter.CurrencyConverterService;
import com.dpcode123.products.exception.exceptions.NoContentFoundException;
import com.dpcode123.products.exception.exceptions.TransactionFailedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.dpcode123.products.product.MockProducts.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CurrencyConverterService currencyConverterService;
    @Mock
    private CodeGeneratorService codeGeneratorService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findAllProducts() {
        List<Product> mockProducts = getMockProducts(10);

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<ProductDTO> productDTOs = productService.findAllProducts();

        assertNotNull(productDTOs);
        assertEquals(mockProducts.size(), productDTOs.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findProductsPaginated() {
        ProductsPage productsPage = new ProductsPage();
        List<Product> mockProducts = getMockProducts(10);
        Page<Product> mockPage = new PageImpl<>(mockProducts);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<ProductDTO> productDTOPage = productService.findProductsPaginated(productsPage);

        assertNotNull(productDTOPage);
        assertEquals(mockProducts.size(), productDTOPage.getContent().size());

        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findProductById() {
        Long productId = 1L;
        Product mockProduct = getMockProductWithId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        ProductDTO productDTO = productService.findProductById(productId);

        assertNotNull(productDTO);
        assertEquals(mockProduct.getName(), productDTO.name());
        assertEquals(mockProduct.getDescription(), productDTO.description());
        assertEquals(mockProduct.getPrice_eur(), productDTO.priceEur());
        assertEquals(mockProduct.getIs_available(), productDTO.isAvailable());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void findProductById_ProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoContentFoundException.class, () -> productService.findProductById(productId));

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void findProductByCode() {
        String productCode = "A1B2C3D4E5";
        Product mockProduct = getMockProductWithId(1L);

        when(productRepository.findByCode(productCode)).thenReturn(Optional.of(mockProduct));

        ProductDTO productDTO = productService.findProductByCode(productCode);

        assertNotNull(productDTO);
        assertEquals(mockProduct.getName(), productDTO.name());
        assertEquals(mockProduct.getDescription(), productDTO.description());
        assertEquals(mockProduct.getPrice_eur(), productDTO.priceEur());
        assertEquals(mockProduct.getIs_available(), productDTO.isAvailable());

        verify(productRepository, times(1)).findByCode(productCode);
    }

    @Test
    public void testFindProductByCode_ProductNotFound() {
        String productCode = "A1B2C3D4E5";

        when(productRepository.findByCode(productCode)).thenReturn(Optional.empty());

        assertThrows(NoContentFoundException.class, () -> productService.findProductByCode(productCode));

        verify(productRepository, times(1)).findByCode(productCode);
    }

    @Test
    public void addProduct() {
        ProductRequest productRequest = getMockProductRequest();
        Product newProduct = getMockProductFromProductRequest(productRequest);

        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        ProductDTO addedProductDTO = productService.addProduct(productRequest);

        assertNotNull(addedProductDTO);
        assertEquals(productRequest.getName(), addedProductDTO.name());
        assertEquals(productRequest.getDescription(), addedProductDTO.description());
        assertEquals(productRequest.getPriceEur(), addedProductDTO.priceEur());
        assertEquals(productRequest.getIsAvailable(), addedProductDTO.isAvailable());

        verify(codeGeneratorService, times(1)).generateUniqueProductCode();
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void addProduct_TransactionFailed() {
        ProductRequest productRequest = getMockProductRequest();

        when(productRepository.save(any(Product.class))).thenThrow(TransactionFailedException.class);

        assertThrows(TransactionFailedException.class, () -> productService.addProduct(productRequest));

        verify(codeGeneratorService, times(1)).generateUniqueProductCode();
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void editProduct() {
        Long productId = 1L;
        ProductRequest productRequest = getMockProductRequest();

        Product existingProduct = getMockProductFromProductRequest(productRequest);
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        ProductDTO productDTO = productService.editProduct(productId, productRequest);

        assertNotNull(productDTO);
        assertEquals(productRequest.getName(), productDTO.name());
        assertEquals(productRequest.getDescription(), productDTO.description());
        assertEquals(productRequest.getPriceEur(), productDTO.priceEur());
        assertEquals(productRequest.getIsAvailable(), productDTO.isAvailable());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void editProduct_ProductNotFound() {
        Long productId = 1L;
        ProductRequest productRequest = getMockProductRequest();

        Product existingProduct = getMockProductFromProductRequest(productRequest);
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenThrow(NoContentFoundException.class);

        assertThrows(NoContentFoundException.class, () -> productService.editProduct(productId, productRequest));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(existingProduct);
    }

    @Test
    public void editProduct_TransactionFailed() {
        Long productId = 1L;
        ProductRequest productRequest = getMockProductRequest();

        Product existingProduct = getMockProductFromProductRequest(productRequest);
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenThrow(TransactionFailedException.class);

        assertThrows(TransactionFailedException.class, () -> productService.editProduct(productId, productRequest));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void deleteProduct() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.removeById(productId)).thenReturn(1L);

        Long deletedProducts = productService.deleteProduct(productId);

        assertEquals(1L, deletedProducts);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).removeById(productId);
    }

    @Test
    public void deleteProduct_ProductNotFound() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(NoContentFoundException.class, () -> productService.deleteProduct(productId));

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).removeById(productId);
    }

    @Test
    public void deleteProduct_TransactionFailed() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.removeById(productId)).thenReturn(0L);

        assertThrows(TransactionFailedException.class, () -> productService.deleteProduct(productId));

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).removeById(productId);
    }
}