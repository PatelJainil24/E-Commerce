package com.app.service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.dto.ProductRequestDTO;
import com.app.dto.ProductResponseDTO;
import com.app.dto.ProductStockUpdateDTO;
import com.app.exception.ProductNotFoundException;
import com.app.models.Product;
import com.app.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public ProductResponseDTO addProduct(ProductRequestDTO request) {
		
		Product product = new Product();
		BeanUtils.copyProperties(request, product);
		//System.out.println("Product Request: "+product);
		product.setStatus("ACTIVE");
		Product save = productRepository.save(product);
		return mapToDTO(save);
	}
	
	@Override
	public void addProductImage(ProductRequestDTO request) {
		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStatus(request.getStatus());
		product.setStock(request.getStock());
		BeanUtils.copyProperties(request, product);
		if(request.getImage() !=null && !request.getImage().isEmpty())
		{
			try {
				product.setImage(request.getImage().getBytes());	
			} catch (Exception e) {	
				e.printStackTrace();
			}
		}
		productRepository.save(product);
	}
	
	
	ProductResponseDTO mapToDTO(Product product) {
		
		ProductResponseDTO dto = new ProductResponseDTO();
		BeanUtils.copyProperties(product, dto);
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setStatus(product.getStatus());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		
		if(product.getImage() != null) {
			//converting byte[] to base64 String
			String base64Image = Base64.getEncoder().encodeToString(product.getImage());
			String imageDataUrl = "data:image/jpeg;base64," + base64Image;
			dto.setImageData(imageDataUrl);
		}
		return dto;
	}

	@Override
	public ProductResponseDTO getProductById(Long productId) {
//		productRepository.findById(productId).map(product -> mapToDTO(product))
//			.orElseThrow(() -> new RuntimeException("Product not found"));
		return productRepository.findById(productId).map(this::mapToDTO)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
	}


	@Override
	public BigDecimal getPriceById(Long productId) {
		
		return productRepository.findById(productId).map(Product::getPrice)
				.orElseThrow(() -> new ProductNotFoundException("Product not Found"));
	}

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		
		return productRepository.findAll().stream()
				.map(this::mapToDTO)
				.toList();
	}


	@Override
	public void updateStock(List<ProductStockUpdateDTO> updates) {
		
		for(ProductStockUpdateDTO dto: updates) {
			Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new ProductNotFoundException("Product Not found with Id: "+dto.getProductId()));
			
			if(product.getStock() < dto.getQuantity()) {
				throw new RuntimeException("Insufficient stock for product Id: "+dto.getProductId());
			}
			
			product.setStock(product.getStock() - dto.getQuantity());
			productRepository.save(product);
		}
		
	}


	@Override
	public boolean isProductExists(Long productId) {
		
		return productRepository.existsById(productId);
	}


	@Override
	public ProductResponseDTO getProductByIdImage(Long productId) {
		
		return productRepository.findById(productId).map(this::mapToDTO)
				.orElseThrow(() -> new ProductNotFoundException("Product Not Found with Id: " + productId));
		
//		Product product = productRepository.findById(productId)
//			.orElseThrow(() -> new ProductNotFoundException("Product Not Found with Id: " + productId));
//		
//		ProductResponseDTO dto = new ProductResponseDTO();
//		BeanUtils.copyProperties(product, dto);
//		
//			// Convert byte[] to Base64 string
//		if(product.getImage() !=null) {
//			String base64Image = Base64.getEncoder().encodeToString(product.getImage());
//			String imageDataUrl = "data:image/jpeg;base64," + base64Image;
//			dto.setImageData(imageDataUrl); // assuming you have setImageData() in DTO
//		}
//		return dto;
		
		
	}


	@Override
	public List<ProductResponseDTO> getAllProductWithImage() {
		
		return productRepository.findAll().stream()
				.filter(e -> e.getStatus().equals("ACTIVE"))
				.map(this::mapToDTO)
				.toList();
//		List<Product> products = productRepository.findAll();
//		
//		return products.stream().filter(a -> a.getStatus().equals("ACTIVE")).map(product -> {
//			ProductResponseDTO dto = new ProductResponseDTO();
//			BeanUtils.copyProperties(product, dto);
//
//			if (product.getImage() != null) {
//				String base64Image = Base64.getEncoder().encodeToString(product.getImage());
//				String imageDataUrl = "data:image/jpeg;base64," + base64Image;
//				dto.setImageData(imageDataUrl);
//			}
//
//			return dto;
//		}).collect(Collectors.toList());
	}

	@Override
	public void editProductImage(Long productId, ProductRequestDTO request) {
		
		log.info("Service : editProductImage call");
		System.out.println("ProductId : "+ productId);
		System.out.println("ProductRequestDTO : "+request);
		
		
		
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException("Product Not Found with Id: " + productId));
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		
		if(request.getImage() !=null && !request.getImage().isEmpty()) {
			try {
				product.setImage(request.getImage().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to update product image", e);
			}
		}
		
		productRepository.save(product);
	}

	@Override
	public List<ProductResponseDTO> searchProducts(String keyword) {
			
		return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
		.stream()
		.filter(a -> a.getStatus().equals("ACTIVE"))
		.map(this::mapToDTO)
		.toList();
		
	}


	@Override
	public List<ProductResponseDTO> getAllProductFilter(Specification<Product> spec) {
		
		List<Product> filteredProducts = productRepository.findAll(spec);
		
		
		return filteredProducts.stream()
				.map(this::mapToDTO)
				.toList();
	}
	
	
	
	
	

}
