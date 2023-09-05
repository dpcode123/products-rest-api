package com.dpcode123.products.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.dpcode123.products.product.MockProducts.getMockProductRequest;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String REQUEST_MAPPING = "/api/product";

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(REQUEST_MAPPING + "/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    void findAllPaginated() throws Exception {
        int pageSize = 10;
        String pageNumber = "2";
        String sortDirection = "ASC";

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING + "/paginated")
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pageNumber", pageNumber)
                        .param("sortDirection", sortDirection)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    @Sql("/insert-product.sql")
    void findProductById() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING + "/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(notNullValue())))
                .andExpect(jsonPath("$.code", is(notNullValue())));
    }

    @Test
    @Sql("/insert-product.sql")
    void findProductByCode() throws Exception {
        String productCode = "ABCDE12345";

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING + "/code/{productCode}", productCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(notNullValue())))
                .andExpect(jsonPath("$.code", is(notNullValue())));
    }

    @Test
    void addProduct() throws Exception {
        ProductRequest productRequest = getMockProductRequest();

        mockMvc.perform(post(REQUEST_MAPPING + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(notNullValue())))
                .andExpect(jsonPath("$.code", is(notNullValue())));;
    }

    @Test
    @Sql("/insert-product.sql")
    void editProduct() throws Exception {
        Long productId = 1L;
        ProductRequest productRequest = getMockProductRequest();

        mockMvc.perform(MockMvcRequestBuilders.put(REQUEST_MAPPING + "/edit/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(notNullValue())))
                .andExpect(jsonPath("$.code", is(notNullValue())));
    }

    @Test
    @Sql("/insert-product.sql")
    void deleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/delete/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(productId.toString()));
    }

}
