package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.models.CartItem;


@Repository
public interface CartRepository extends JpaRepository<CartItem, Long>{

	List<CartItem> findByUserId(Long userId);
	void deleteByUserId(Long userId);
	void deleteByUserIdAndProductId(Long userId,Long ProductId);
	Optional<CartItem> findByUserIdAndProductId(Long userId,Long ProductId);
}
