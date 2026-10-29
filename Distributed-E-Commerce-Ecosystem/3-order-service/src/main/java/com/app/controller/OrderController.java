package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.OrderResponseDTO;
import com.app.dto.PlaceOrderRequestDTO;
import com.app.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

	
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderResponseDTO> placedOrder(@RequestBody PlaceOrderRequestDTO request) {
		log.info("OrderController : placedOrder");
		OrderResponseDTO response = orderService.placeOrder(request);
		return new ResponseEntity<OrderResponseDTO>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId){
		log.info("OrderController : getOrder");
		OrderResponseDTO result = orderService.getOrderById(orderId);
		return new ResponseEntity<OrderResponseDTO>(result,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<OrderResponseDTO>> getOrderByUser(@PathVariable Long userId){
		log.info("OrderController : getOrderByUser");
		List<OrderResponseDTO> result = orderService.getOrderByUser(userId);
		return new ResponseEntity<List<OrderResponseDTO>>(result,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/status/{status}")
	public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId,
												  @PathVariable String status){
		log.info("OrderController : updateOrderStatus");
		orderService.updateOrderStatus(orderId, status);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
		log.info("OrderController : getAllOrders");
		List<OrderResponseDTO> result = orderService.getAllOrders();
		return ResponseEntity.ok(result);
	}
	
	
	
}
