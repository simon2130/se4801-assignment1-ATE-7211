// ATE/7211/14
package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.model.Product;
import com.shopwave.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_success() {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(BigDecimal.valueOf(1000));
        request.setStock(5);

        Product saved = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .stock(5)
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        var result = productService.createProduct(request);

        assertEquals("Laptop", result.getName());
        assertEquals(5, result.getStock());
    }

    @Test
    void updateStock_negative_shouldThrow() {
        Product product = Product.builder()
                .id(1L)
                .stock(2)
                .build();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        assertThrows(IllegalArgumentException.class, () ->
                productService.updateStock(1L, -5)
        );
    }
}