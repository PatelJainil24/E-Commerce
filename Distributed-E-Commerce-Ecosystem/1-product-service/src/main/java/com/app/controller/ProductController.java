package com.app.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ProductRequestDTO;
import com.app.dto.ProductResponseDTO;
import com.app.dto.ProductStockUpdateDTO;
import com.app.models.Product;
import com.app.service.ProductService;
import com.app.service.ProductSpecification;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
//	@PostMapping
//	public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO request) {
//		log.info("ProductController : addProduct");		
//		ProductResponseDTO product = productService.addProduct(request);
//		return new ResponseEntity<ProductResponseDTO>(product, HttpStatus.CREATED);
//	}
	
	
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addProdcut(@ModelAttribute ProductRequestDTO request) {
		log.info("ProductController addProduct");
		productService.addProductImage(request);
		return ResponseEntity.ok("Product saved Successfully");
	}
	
	@PutMapping(value = "/image/{productId}")
	public ResponseEntity<String> updateProduct(@RequestBody ProductRequestDTO request, @PathVariable Long productId) {
		log.info("ProductController updateProduct");
		productService.editProductImage(productId, request);
		return ResponseEntity.ok("Product Updated Successfully");
	}
	
	@GetMapping("/image/{productId}")
    public ProductResponseDTO getProductImage(@PathVariable Long productId) {
    	log.info("ProductController getProduct");
        return productService.getProductByIdImage(productId);
    }
    
    @GetMapping("/image")
    public ResponseEntity<List<ProductResponseDTO>> fetchAllProducts() {
        List<ProductResponseDTO> productList = productService.getAllProductWithImage();
        return ResponseEntity.ok(productList);
    }
    
    @PutMapping("/update-stock")
    public ResponseEntity<Void> updateProductStock(@RequestBody List<ProductStockUpdateDTO> updates){
    	log.info("ProductController :updateProductStock");
    	productService.updateStock(updates);
    	
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/exists/{productId}")
    boolean isProductExists(@PathVariable Long productId) {
    	log.info("ProductController :isProductExist");
    	return productService.isProductExists(productId);
    }
    
    @GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(@RequestParam String keyword){
    	log.info("ProductController :searchProducts");
    	
    	return productService.searchProducts(keyword);
    }
    
    @GetMapping("/filter")
    public List<ProductResponseDTO> getAllProducts(
    		@RequestParam(required = false) String name,
        	@RequestParam(required = false) String category,
        	@RequestParam(required = false) Double price,
        	@RequestParam(required = false) Boolean available,
        	@RequestParam(required = false) String brand
	) {
    	log.info("ProductController :getAllProducts :By filters");
    	Specification<Product> spec = ProductSpecification.filterByAtributes(name, category, price, available, brand);
    	List<ProductResponseDTO> products = productService.getAllProductFilter(spec);
    	return products;
    }
	
}
