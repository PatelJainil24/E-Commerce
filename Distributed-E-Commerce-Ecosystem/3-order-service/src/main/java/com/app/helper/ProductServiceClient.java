package com.app.helper;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.dto.ProductResponseDTO;
import com.app.dto.ProductStockUpdateDTO;

@FeignClient(name = "product-service",path="/products")
public interface ProductServiceClient {

	
	@GetMapping("/exists/{productId}")
    boolean isProductExists(@PathVariable Long productId);
	
	@GetMapping("/image/{productId}")
    public ProductResponseDTO getProductImage(@PathVariable Long productId);
	
	@PutMapping("/update-stock")
    void updateProductStock(@RequestBody List<ProductStockUpdateDTO> updates);
}
