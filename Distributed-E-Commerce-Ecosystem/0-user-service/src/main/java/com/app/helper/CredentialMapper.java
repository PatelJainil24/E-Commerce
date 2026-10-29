package com.app.helper;

import org.mapstruct.Mapper;

import com.app.dto.CredentialDto;
import com.app.models.Credential;

@Mapper(componentModel = "spring")
public interface CredentialMapper {

	
	CredentialDto toDto(Credential credential);
	Credential toEntity(CredentialDto credentialDto);
}
