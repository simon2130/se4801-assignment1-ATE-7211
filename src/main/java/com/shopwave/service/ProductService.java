//ATE/7211/14
package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import com.shopwave.exception.ProductNotFoundException;
import com.shopwave.model.Product;
import com.shopwave.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        return ProductMapper.toDTO(productRepository.save(product));
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toDTO);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toDTO(product);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {

        List<Product> results;

        if (keyword != null && maxPrice != null) {
            results = productRepository.findByNameContainingIgnoreCase(keyword)
                    .stream()
                    .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                    .toList();

        } else if (keyword != null) {
            results = productRepository.findByNameContainingIgnoreCase(keyword);

        } else if (maxPrice != null) {
            results = productRepository.findByPriceLessThanEqual(maxPrice);

        } else {
            results = productRepository.findAll();
        }

        return results.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public ProductDTO updateStock(Long id, int delta) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int newStock = product.getStock() + delta;

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        product.setStock(newStock);

        return ProductMapper.toDTO(product);
    }
}