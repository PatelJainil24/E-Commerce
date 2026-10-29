package com.app.service;

import java.util.List;

import com.app.dto.OrderResponseDTO;
import com.app.dto.PlaceOrderRequestDTO;

public interface OrderService {

	OrderResponseDTO placeOrder(PlaceOrderRequestDTO request); // create an order
	OrderResponseDTO getOrderById(Long orderId); // View Order details
	List<OrderResponseDTO> getOrderByUser(Long userId); //get all orders by user
	void updateOrderStatus(Long orderId,String status); // change order status
	List<OrderResponseDTO> getAllOrders();
}
