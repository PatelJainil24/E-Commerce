package com.app.product.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

	private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
//    private String imageData;
    private String status;
}
