//ATE/7211/14
package com.shopwave.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String categoryName;
}