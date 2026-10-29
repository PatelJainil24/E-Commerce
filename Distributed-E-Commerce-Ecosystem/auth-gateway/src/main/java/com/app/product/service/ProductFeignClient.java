package com.app.product.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.product.dto.ProductRequestDTO;
import com.app.product.dto.ProductResponseDTO;



@FeignClient(name = "PRODUCT-SERVICE",path = "/products ")
public interface ProductFeignClient {

	
	@PostMapping
	public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO request);
	
}
