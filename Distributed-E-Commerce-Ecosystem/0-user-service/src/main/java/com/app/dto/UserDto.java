package com.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder // //It create objects in a clean, readable, and flexible way instead of using constructors
public class UserDto {

	
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	
	@JsonInclude(value = Include.NON_NULL) //Include this field in JSON only if it is NOT null
	private CredentialDto credential;
}
