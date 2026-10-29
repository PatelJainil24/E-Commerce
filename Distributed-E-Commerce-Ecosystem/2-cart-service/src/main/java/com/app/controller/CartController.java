package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.CartItemRequestDTO;
import com.app.dto.CartItemResponseDTO;
import com.app.service.CartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

	
	@Autowired
	private CartService cartService;
	
	
	@PostMapping("/add")
	public ResponseEntity<CartItemResponseDTO> addItemToCart(@RequestBody CartItemRequestDTO request) {
		log.info("CartController :addItemToCart ");
		CartItemResponseDTO response = cartService.addToCart(request);
		return new ResponseEntity<CartItemResponseDTO>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CartItemResponseDTO> updateCartItem(@RequestBody CartItemRequestDTO request) {
		log.info("CartController :updateCartItem ");
		CartItemResponseDTO response = cartService.updateQuantity(request);
		return new ResponseEntity<CartItemResponseDTO>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<CartItemResponseDTO>> getCartByUser(@PathVariable Long userId){
		log.info("CartController :getCartByUser ");
		List<CartItemResponseDTO> response = cartService.getUserCart(userId);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<Void> removeItemFromCart(@PathVariable Long productId, @RequestParam Long userId){
		log.info("CartController :removeItemFromCart ");
		cartService.removeItem(userId, productId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<Void> clearUserCart(@PathVariable Long userId){
		log.info("CartController :clearUserCart ");
		cartService.clearCart(userId);
		return ResponseEntity.noContent().build();
	}
	
}
