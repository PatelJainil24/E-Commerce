package com.app.dto;

import com.app.models.RoleBasedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialDto {

	
	private Long credentialId;
	private String username;
	private String password;
	private RoleBasedAuthority roleBasedAuthority;
	
}
