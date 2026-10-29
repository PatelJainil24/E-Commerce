package com.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.CartItemResponseDTO;
import com.app.dto.OrderItemResponseDTO;
import com.app.dto.OrderResponseDTO;
import com.app.dto.PlaceOrderRequestDTO;
import com.app.dto.ProductResponseDTO;
import com.app.dto.UserDto;
import com.app.helper.CartServiceClient;
import com.app.helper.ProductServiceClient;
import com.app.helper.UserFeignClient;
import com.app.models.Order;
import com.app.models.OrderItem;
import com.app.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private ProductServiceClient productServiceClient;
	
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private CartServiceClient cartServiceClient;
	
	
	@Override
	public OrderResponseDTO placeOrder(PlaceOrderRequestDTO request) {
		
		//User validate:
		validateUser(request.getUserId());
		
		List<CartItemResponseDTO> cartItems = fetchCartItems(request.getUserId());
		//validateCartItems:
		validateCartItems(cartItems);
		
		//Order : total Price:
		BigDecimal total = calculateTotalPrice(cartItems);
		System.out.println("Total Price : " + total);
		
		
		List<OrderItem> orderItems = buildOrderItems(cartItems);
		
		//create Order:
		Order order = createOrder(request.getUserId(),total,orderItems);
		
		
		Order savedOrder = orderRepository.save(order);
		
		
		return mapToDto(savedOrder);
	}

	//mapToDto: 
	private OrderResponseDTO mapToDto(Order order) {
		
		OrderResponseDTO orderDto = new OrderResponseDTO();
		orderDto.setOrderId(order.getId());
		BeanUtils.copyProperties(order, orderDto,"items");
		
		List<OrderItemResponseDTO> orderItemList = order.getItems().stream()
						.map(item -> {
							OrderItemResponseDTO itemDto = new OrderItemResponseDTO();
							
							ProductResponseDTO product = productServiceClient.getProductImage(item.getProductId());
							itemDto.setProduct(product);
							BeanUtils.copyProperties(item, itemDto);
							return itemDto;
						}).toList();
		
		
		orderDto.setItems(orderItemList);
		
		
		return orderDto;
	}

	private Order createOrder(Long userId, BigDecimal total, List<OrderItem> orderItems) {
		Order order = new Order();
		order.setUserId(userId);
		order.setTotalPrice(total);
		order.setStatus("PLACED");
		
		for(OrderItem item: orderItems) {
			item.setOrder(order);
		}
		
		order.setItems(orderItems);
		
		return order;
		
		
	}

	private List<OrderItem> buildOrderItems(List<CartItemResponseDTO> cartItems) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		for(CartItemResponseDTO item: cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(item.getProductId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(item.getProduct().getPrice());
			orderItems.add(orderItem);
		}
		
		return orderItems;
		
	}

	//CalculateTotal Price:
	private BigDecimal calculateTotalPrice(List<CartItemResponseDTO> cartItems) {
		BigDecimal total = BigDecimal.ZERO;
		for(CartItemResponseDTO item: cartItems) {
			BigDecimal price = item.getProduct().getPrice();
			System.out.println("CartItem Price: " + price);
			total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
		}
		
		return total;
	}

	private void validateCartItems(List<CartItemResponseDTO> cartItems) {
		
		if(cartItems == null || cartItems.isEmpty()) {
			throw new RuntimeException("Cart is Empty...");
		}
		
	}

	//Fetch Cart Items by UserId:
	private List<CartItemResponseDTO> fetchCartItems(Long userId) {
		
		List<CartItemResponseDTO> cartItems = cartServiceClient.getCartByUser(userId);
		return cartItems;
	}

	//UserValidation...
	private UserDto validateUser(Long userId) {
		log.info("User Id {}:"+userId);
		
		try {
			return userFeignClient.findById(userId);
		} catch (Exception e) {
			throw new RuntimeException("User not found with UserId : "+userId);
		}
		
		
	}
	
	
	//Get Order by OrderId : 
	@Override
	public OrderResponseDTO getOrderById(Long orderId) {
			
		return orderRepository.findById(orderId)
			.map(this::mapToDto)
			.orElseThrow(() -> new RuntimeException("Order not found with OrderId: "+orderId));
		 
	}

	@Override
	public List<OrderResponseDTO> getOrderByUser(Long userId) {
		return orderRepository.findByUserId(userId).stream()
			.map(this::mapToDto)
			.sorted(Comparator.comparing(OrderResponseDTO::getOrderId).reversed())
			.toList();
		 
	}

	@Override
	public void updateOrderStatus(Long orderId, String status) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new RuntimeException("Order Not Found with orderId: "+orderId));
		
		if(!isValidStatus(status)) {
			throw new IllegalArgumentException("Invalid order status: "+status);
		}
		
		order.setStatus(status);
		orderRepository.save(order);
		
	}

	private boolean isValidStatus(String status) {
		List<String> validStatuses = Arrays.asList("PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED","PLACED");
		return validStatuses.contains(status);
	}

	@Override
	public List<OrderResponseDTO> getAllOrders() {
		return orderRepository.findAll()
			.stream()
			.map(this::mapToDto)
			.toList();
		 
	}

}
