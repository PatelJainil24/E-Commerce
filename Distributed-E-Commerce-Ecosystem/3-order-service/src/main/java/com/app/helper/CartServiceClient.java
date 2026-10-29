package com.app.helper;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.dto.CartItemResponseDTO;



@FeignClient(name = "cart-service",path = "/cart")
public interface CartServiceClient {

	
	@GetMapping("/{userId}")
	List<CartItemResponseDTO> getCartByUser(@PathVariable Long userId);
	
	@DeleteMapping("/clear/{userId}")
	void clearUserCart(@PathVariable Long userId);
}
