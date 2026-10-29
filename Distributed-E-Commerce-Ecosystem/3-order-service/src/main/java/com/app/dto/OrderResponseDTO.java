package com.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderResponseDTO {

	private Long orderId;
    private Long userId;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime placedAt;
    private List<OrderItemResponseDTO> items;
}
