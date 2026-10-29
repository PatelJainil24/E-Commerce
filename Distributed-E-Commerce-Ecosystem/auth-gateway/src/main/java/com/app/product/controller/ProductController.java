package com.app.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.product.dto.ProductRequestDTO;
import com.app.product.dto.ProductResponseDTO;
import com.app.product.service.ProductFeignClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
	
	@Autowired
	private ProductFeignClient productFeignClient;
	
	
	// 1. Add product using JSON
	@PostMapping
	public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO request) {
		log.info("ProductController : addProduct");		
		ProductResponseDTO product = productFeignClient.addProduct(request).getBody();
		return new ResponseEntity<ProductResponseDTO>(product, HttpStatus.CREATED);
	}
	
}
