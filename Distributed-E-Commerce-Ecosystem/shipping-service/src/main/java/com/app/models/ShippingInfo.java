package com.app.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "shipping_info")
public class ShippingInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long orderId;
	
	private String trackingNumber;
	private String shippingMethod;
	private LocalDateTime shippedAt;
	private LocalDateTime deliveryDate;
	private String status;
	private String carrier;
}
