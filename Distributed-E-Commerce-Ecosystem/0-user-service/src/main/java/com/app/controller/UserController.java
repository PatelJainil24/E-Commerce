package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserDto;
import com.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
		log.info("UserController :: save ");
		UserDto saveUser = userService.save(userDto);
		return new ResponseEntity<UserDto>(saveUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
		log.info("UserController :: fetch user details");
		UserDto saveUser = userService.findById(userId);
		return new ResponseEntity<UserDto>(saveUser, HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> update(@PathVariable Long userId, @RequestBody UserDto userDto){
		log.info("UserController :: update");
		UserDto updateUser = userService.update(userId, userDto);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Boolean> delete(@PathVariable Long userId) {
		log.info("UserController :: Delete");
		userService.deleteByID(userId);
		return ResponseEntity.ok(true);
		
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		log.info("UserController :: getAllUsers");
		
		return new ResponseEntity<List<UserDto>>(userService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
		log.info("UserController :: getUserByUsername");
		
		return new ResponseEntity<UserDto>(userService.findByUsername(username), HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
