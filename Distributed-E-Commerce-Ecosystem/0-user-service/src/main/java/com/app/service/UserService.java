package com.app.service;

import java.util.List;

import com.app.dto.UserDto;

public interface UserService {

	UserDto save(UserDto userDto);
	UserDto findById(Long userId);
	List<UserDto> findAll();
	UserDto update(Long userId,UserDto userDto);
	void deleteByID(Long userId);
	UserDto findByUsername(String username);
	UserDto updateUser(Long userId,UserDto userDto);
}
