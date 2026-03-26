// ATE/7211/14
package com.shopwave.repository;

import com.shopwave.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNameContainingIgnoreCase_shouldWork() {
        Product p = Product.builder()
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .stock(5)
                .build();

        productRepository.save(p);

        List<Product> results = productRepository
                .findByNameContainingIgnoreCase("lap");

        assertFalse(results.isEmpty());
        assertEquals("Laptop", results.get(0).getName());
    }
}