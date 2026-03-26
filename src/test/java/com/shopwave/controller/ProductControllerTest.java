// ATE/7211/14
package com.shopwave.controller;

import com.shopwave.dto.ProductDTO;
import com.shopwave.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProducts_shouldReturn200() throws Exception {
        ProductDTO dto = ProductDTO.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .build();

        Page<ProductDTO> page = new PageImpl<>(List.of(dto));

        Mockito.when(productService.getAllProducts(Mockito.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    void getProduct_notFound_shouldReturn404() throws Exception {
        Mockito.when(productService.getProductById(999L))
                .thenThrow(new com.shopwave.exception.ProductNotFoundException(999L));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}