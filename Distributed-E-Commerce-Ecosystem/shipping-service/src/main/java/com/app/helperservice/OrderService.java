package com.app.helperservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.app.dto.OrderResponseDTO;

@FeignClient(name ="order-service",path="/orders")
public interface OrderService {

	
	@GetMapping("/{orderId}")
	OrderResponseDTO getOrder(@PathVariable Long orderId);
	
	@PutMapping("/{orderId}/status/{status}")
	Void updateOrderStatus(@PathVariable Long orderId,@PathVariable String status);
												  
}
