package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.dto.ShippingResponseDTO;
import com.app.models.ShippingInfo;


@Repository
public interface ShippingRepository extends JpaRepository<ShippingInfo, Long>{
	
	Optional<ShippingInfo> findByOrderId(Long orderId);

}
