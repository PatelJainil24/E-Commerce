package com.app.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.dto.PaymentRequestDTO;
import com.app.dto.PaymentResponseDTO;
import com.app.models.Payment;
import com.app.repository.PaymentRepository;

public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
		Payment payment;
	    Optional<Payment> existingPayment = paymentRepository.findByOrderId(request.getOrderId());

	    if (existingPayment.isPresent()) {
	        // Update existing payment
	        payment = existingPayment.get();
	        BeanUtils.copyProperties(request, payment, "id", "status"); // avoid overwriting ID or status if needed
	        payment.setStatus("SUCCESS"); // explicitly set new status
	    } else {
	        // Create new payment
	        payment = new Payment();
	        BeanUtils.copyProperties(request, payment);
	        payment.setStatus("SUCCESS");
	    }

		Payment dbPayment = paymentRepository.save(payment);
	
		return mapToDTO(dbPayment);
	}

	private PaymentResponseDTO mapToDTO(Payment dbPayment) {
		PaymentResponseDTO dto = new PaymentResponseDTO();
		BeanUtils.copyProperties(dbPayment, dto);
		dto.setPaymentId(dbPayment.getId());
		return dto;
	}

	@Override
	public PaymentResponseDTO getPaymentDetails(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
