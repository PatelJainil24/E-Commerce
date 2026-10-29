package com.app.user.dto;

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
