package com.app.service;

import com.app.dto.PaymentRequestDTO;
import com.app.dto.PaymentResponseDTO;

public interface PaymentService {

	PaymentResponseDTO processPayment(PaymentRequestDTO request);
	
	
	PaymentResponseDTO getPaymentDetails(Long orderId);
}
