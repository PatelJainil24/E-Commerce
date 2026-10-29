package com.app.helper;

import org.mapstruct.Mapper;

import com.app.dto.UserDto;
import com.app.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toDto(User user);
	User toEntity(UserDto userDto);
}
