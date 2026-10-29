package com.app.service;

import com.app.dto.ChangePasswordDto;
import com.app.dto.CredentialDto;

public interface CredentialService {

	CredentialDto findByUsername(String username);
	
	String changePassword(ChangePasswordDto dto);
}
