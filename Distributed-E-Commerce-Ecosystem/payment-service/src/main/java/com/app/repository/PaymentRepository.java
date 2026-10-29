package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
	Payment findByRazorpayOrderId(String razroOrderId);
	
	Optional<Payment> findByOrderId(Long orderId);

}
