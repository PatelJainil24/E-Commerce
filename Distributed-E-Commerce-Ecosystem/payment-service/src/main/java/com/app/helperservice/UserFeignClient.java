package com.app.helperservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.dto.UserDto;

@FeignClient(name="user-service",path="/api/users")
public interface UserFeignClient {

	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findById(@PathVariable Long userId);
}
