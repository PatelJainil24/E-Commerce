package com.app.product.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDTO {

	private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    
//    private MultipartFile image;
//    private String status;
}
