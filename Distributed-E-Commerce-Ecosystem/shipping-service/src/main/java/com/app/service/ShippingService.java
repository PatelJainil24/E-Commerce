package com.app.service;

import com.app.dto.ShippingRequestDTO;
import com.app.dto.ShippingResponseDTO;

public interface ShippingService {

	ShippingResponseDTO shipOrder(Long orderId,ShippingRequestDTO request);
	ShippingResponseDTO getShippingDetails(Long orderId);
	void updateShippingStatus(Long orderId,String status);
}
