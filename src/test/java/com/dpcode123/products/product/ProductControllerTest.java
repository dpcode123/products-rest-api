package com.dpcode123.products.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.dpcode123.products.product.MockProducts.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductServiceImpl productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String REQUEST_MAPPING = "/api/product";

    @Test
    void findAll() throws Exception {
        List<ProductDTO> productDTOList = getMockProductDTOs(10);

        Mockito.when(productService.findAllProducts()).thenReturn(productDTOList);

        mockMvc.perform(get(REQUEST_MAPPING + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTOList)));
    }

    @Test
    void findAllPaginated() throws Exception {
        int pageSize = 10;
        String pageNumber = "2";
        String sortDirection = "ASC";

        List<ProductDTO> productDTOList = getMockProductDTOs(10);
        Page<ProductDTO> productPage = new PageImpl<>(productDTOList);

        Mockito.when(productService.findProductsPaginated(Mockito.any(ProductsPage.class))).thenReturn(productPage);

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING + "/paginated")
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pageNumber", pageNumber)
                        .param("sortDirection", sortDirection)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productPage)));
    }

    @Test
    void findProductById() throws Exception {
        Long productId = 123L;
        ProductDTO productDTO = getMockProductDTOs(1).get(0);

        Mockito.when(productService.findProductById(productId)).thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING + "/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTO)));
    }

    @Test
    void findProductByCode() throws Exception {
        String productCode = "ABCDE12345";
        ProductDTO productDTO = getMockProductDTO();

        Mockito.when(productService.findProductByCode(productCode)).thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING + "/code/{productCode}", productCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTO)));
    }

    @Test
    void addProduct() throws Exception {
        ProductRequest productRequest = getMockProductRequest();
        ProductDTO productDTO = getMockProductDTO();

        Mockito.when(productService.addProduct(productRequest)).thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(REQUEST_MAPPING + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTO)));
    }

    @Test
    void editProduct() throws Exception {
        Long productId = 1L;
        ProductRequest productRequest = getMockProductRequest();
        ProductDTO mockProductDTO = getMockProductDTO();

        Mockito.when(productService.editProduct(productId, productRequest)).thenReturn(mockProductDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(REQUEST_MAPPING + "/edit/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockProductDTO)));
    }

    @Test
    void deleteProduct() throws Exception {
        Long productId = 1L;

        Mockito.when(productService.deleteProduct(productId)).thenReturn(productId);

        mockMvc.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING + "/delete/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(productId.toString()));
    }
}