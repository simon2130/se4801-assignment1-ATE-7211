//ATE/7211/14
package com.shopwave.service;

import com.shopwave.dto.ProductDTO;
import com.shopwave.model.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryName(
                        product.getCategory() != null
                                ? product.getCategory().getName()
                                : null
                )
                .build();
    }
}