package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.ChangePasswordDto;
import com.app.dto.CredentialDto;
import com.app.helper.CredentialMapper;
import com.app.models.Credential;
import com.app.repository.CredentialRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor	
public class CredentialServiceImpl implements CredentialService{

	
	@Autowired
	private CredentialRepository credentialRepository;
	
	@Autowired
	private CredentialMapper credentialMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public CredentialDto findByUsername(String username) {
		log.info("CredentialService :: findByUsername({})", username);
		return credentialRepository.findByUsername(username)
				.map(credentialMapper::toDto)
				.orElseThrow(() -> new RuntimeException("Username '" + username + "' not found in DB."));
	}

	@Override
	public String changePassword(ChangePasswordDto dto) {
		
		Credential dbCredential = credentialRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new RuntimeException("User Not Found..."));
		
		if(!passwordEncoder.matches(dto.getOldPassword(), dbCredential.getPassword())) {
			throw new RuntimeException("Old password Is incorrect...");
		}
		
		dbCredential.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		credentialRepository.save(dbCredential);
		
		return "Password Changed successfully";
	}

}
