package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductStockUpdateDTO {

	private Long productId;
    private Integer quantity;
}
