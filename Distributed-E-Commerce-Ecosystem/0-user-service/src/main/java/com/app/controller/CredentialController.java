package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ChangePasswordDto;
import com.app.dto.CredentialDto;
import com.app.service.CredentialService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/credentials")
@Slf4j
public class CredentialController {

	
	@Autowired
	private CredentialService credentialService;
	
	
	@GetMapping("/username/{uname}")
	public ResponseEntity<CredentialDto> findByUsername(@PathVariable("uname") String username) {
		log.info("CredentialController findByUsername");
		CredentialDto result = credentialService.findByUsername(username);
		return new ResponseEntity<CredentialDto>(result, HttpStatus.OK);
	}
	
	
	
	
	@PatchMapping("/change-password")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto dto) {
		log.info("CredentialController : changePassword");
		String result = credentialService.changePassword(dto);
		return ResponseEntity.ok(result);
	}
}
