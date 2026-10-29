package com.app.service;

import java.util.List;

import com.app.dto.CartItemRequestDTO;
import com.app.dto.CartItemResponseDTO;

public interface CartService {

	CartItemResponseDTO addToCart(CartItemRequestDTO request);
	CartItemResponseDTO updateQuantity(CartItemRequestDTO request);
	List<CartItemResponseDTO> getUserCart(Long userId);
	void removeItem(Long userId,Long productid);
	void clearCart(Long userId);
}
