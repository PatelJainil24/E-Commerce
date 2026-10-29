package com.app.dto;

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
	
	
}
