package com.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.OrderResponseDTO;
import com.app.dto.ShippingRequestDTO;
import com.app.dto.ShippingResponseDTO;
import com.app.helperservice.OrderService;
import com.app.models.ShippingInfo;
import com.app.repository.ShippingRepository;


@Service
public class ShippingServiceImpl implements ShippingService{
	
	
	@Autowired
	private ShippingRepository shippingRepository;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public ShippingResponseDTO shipOrder(Long orderId, ShippingRequestDTO request) {
			
		OrderResponseDTO order = orderService.getOrder(orderId);
		
		if(order ==null) {
			throw new RuntimeException("Order Not found");
		}
		
		ShippingInfo shippingInfo = new ShippingInfo();
		shippingInfo.setOrderId(orderId);
		shippingInfo.setShippingMethod(request.getShippingMethod());
		shippingInfo.setCarrier(request.getCarrier());
		shippingInfo.setShippedAt(LocalDateTime.now());
		shippingInfo.setStatus("SHIPPED");
		shippingInfo.setTrackingNumber(generateTrackingNumber(orderId));
		
		ShippingInfo saved = shippingRepository.save(shippingInfo);
		
		orderService.updateOrderStatus(orderId, "SHIPPED");
		
		return mapToDTO(saved);
	}

	private ShippingResponseDTO mapToDTO(ShippingInfo info) {
		
		ShippingResponseDTO dto = new ShippingResponseDTO();
		
		BeanUtils.copyProperties(info, dto);
		
		return dto;
	}

	private String generateTrackingNumber(Long orderId) {
		
		return "TRACK-" + orderId+"-"+ System.currentTimeMillis();
	}

	@Override
	public ShippingResponseDTO getShippingDetails(Long orderId) {
		
		ShippingInfo info = shippingRepository.findByOrderId(orderId)
						.orElseThrow(() -> new RuntimeException("Shipping Info not Found with orderId: "+orderId));
		return mapToDTO(info);
	}

	@Override
	public void updateShippingStatus(Long orderId, String status) {

		ShippingInfo info = shippingRepository.findByOrderId(orderId)
						.orElseThrow(() -> new RuntimeException("Shipping Info not Found with orderId: "+orderId));
		
		info.setStatus(status);
		info.setDeliveryDate("DELIVERED".equals(status)? LocalDateTime.now():null);
		
		shippingRepository.save(info);
		
		if("DELIVERED".equals(status)) {
			orderService.updateOrderStatus(orderId, status);
		}
		
		
	}
	
	 private String fetchTrackingStatus(String trackingNumber) {
	        // Simulate a call to FedEx/UPS API
	        return "IN_TRANSIT"; // or "DELIVERED"
	    }

}
