package com.app.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.app.dto.ProductRequestDTO;
import com.app.dto.ProductResponseDTO;
import com.app.dto.ProductStockUpdateDTO;
import com.app.models.Product;

public interface ProductService {

	ProductResponseDTO addProduct(ProductRequestDTO request);
	void addProductImage(ProductRequestDTO request);
	ProductResponseDTO getProductById(Long productId);
	BigDecimal getPriceById(Long productId);
	List<ProductResponseDTO> getAllProducts();
	void updateStock(List<ProductStockUpdateDTO> updates);
	public boolean isProductExists(Long productId);
	ProductResponseDTO getProductByIdImage(Long productId);
	List<ProductResponseDTO> getAllProductWithImage();
	public List<ProductResponseDTO> searchProducts(String keyword);
	public void editProductImage(Long productId,ProductRequestDTO request);
	List<ProductResponseDTO> getAllProductFilter(Specification<Product> spec);
	
}
