package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.CartItemRequestDTO;
import com.app.dto.CartItemResponseDTO;
import com.app.dto.ProductResponseDTO;
import com.app.dto.UserDto;
import com.app.helper.ProductServiceClient;
import com.app.helper.UserFeignClient;
import com.app.models.CartItem;
import com.app.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService{

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductServiceClient productServiceClient;
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	
	@Override
	public CartItemResponseDTO addToCart(CartItemRequestDTO request) {
		
		validateRequest(request);
		
		
		
		//Fetch product and user
		ProductResponseDTO product = productServiceClient.getProductImage(request.getProductId());
		UserDto user = userFeignClient.findById(request.getUserId());
		
		//check if item already exists in cart
		Optional<CartItem> existingItemOpt = cartRepository.findByUserIdAndProductId(request.getUserId(), request.getProductId());
		
		CartItem item;
		if(existingItemOpt.isPresent()) {
			
			// Item already in cart - update Quantity
			item = existingItemOpt.get();
			item.setQuantity(item.getQuantity() + request.getQuantity()); // increase quantity
			
		}else {
			item = new CartItem();
			item.setUserId(request.getUserId());
			item.setProductId(request.getProductId());
			item.setQuantity(request.getQuantity());
		}
		return mapToDto(cartRepository.save(item),product);
	}

	private CartItemResponseDTO mapToDto(CartItem item, ProductResponseDTO product) {
		
		CartItemResponseDTO dto = new CartItemResponseDTO();
		BeanUtils.copyProperties(item, dto);
		dto.setProduct(product);
		
		return dto;
	}

	private void validateRequest(CartItemRequestDTO request) {
		
		int quantity = request.getQuantity();
		
		if(quantity<=0 || quantity>10) {
			throw new IllegalArgumentException("Quantity must be between 1 to 10");
		}
		
	}

	@Override
	public CartItemResponseDTO updateQuantity(CartItemRequestDTO request) {
		CartItem item = cartRepository.findByUserIdAndProductId(request.getUserId(), request.getProductId())
			.orElseThrow(() -> new RuntimeException("Item not in cart"));
		
		item.setQuantity(request.getQuantity());
		CartItem updatedItem = cartRepository.save(item);
		ProductResponseDTO product = productServiceClient.getProductImage(request.getProductId());
		return mapToDto(updatedItem, product);
	}

	@Override
	public List<CartItemResponseDTO> getUserCart(Long userId) {
		
		return cartRepository.findByUserId(userId).stream()
			.map((cart -> {
				ProductResponseDTO product = productServiceClient.getProductImage(cart.getProductId());
				return mapToDto(cart, product);
			}))
			.toList();
		 
	}

	@Transactional
	public void removeItem(Long userId, Long productid) {
		
		cartRepository.deleteByUserIdAndProductId(userId, productid);
		
	}

	@Transactional
	public void clearCart(Long userId) {
		cartRepository.deleteByUserId(userId);
		
	}

}
