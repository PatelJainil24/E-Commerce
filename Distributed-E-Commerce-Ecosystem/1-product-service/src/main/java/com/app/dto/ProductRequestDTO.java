package com.app.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDTO {

	private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private MultipartFile image;
    private String status;
	@Override
	public String toString() {
		return "ProductRequestDTO [name=" + name + ", description=" + description + ", price=" + price + ", stock="
				+ stock + ", image=" + image + ", status=" + status + "]";
	}
    
    
}
