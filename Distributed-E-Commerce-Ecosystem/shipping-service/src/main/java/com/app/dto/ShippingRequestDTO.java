package com.app.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShippingRequestDTO {
	private Long orderId;
    private String shippingMethod; 
    private String carrier;
}
