package com.app.service;

import java.util.Comparator;
import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.exception.UserNotFoundException;
import com.app.helper.UserMapper;
import com.app.models.Credential;
import com.app.models.User;
import com.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDto save(UserDto userDto) {
		
		User user = userMapper.toEntity(userDto);
		Credential credential = user.getCredential();
		
		//Todo : password Encoding : 
		//secure Password : 
		String encodePassword = passwordEncoder.encode(credential.getPassword());
		credential.setPassword(encodePassword);
		
		//set bidirectional mapping 
		credential.setUser(user);
		user.setCredential(credential);
		
		User saveUser = userRepository.save(user);
		
		return userMapper.toDto(saveUser);
	}

	@Override
	public UserDto findById(Long userId) {
		
//		User user = userRepository.findById(userId)
//									.orElseThrow(() -> new RuntimeException("User Not Found..."));
//		
//		return userMapper.toDto(user);
		
		return userRepository.findById(userId).map(userMapper::toDto)
				.orElseThrow(() -> new UserNotFoundException("User with Id : "+userId+" Not found"));
				
	}

	@Override
	public List<UserDto> findAll() {

		return userRepository.findAll().stream().sorted(Comparator.comparing(User::getUserId))
				.map(userMapper::toDto).toList();
	}

	@Override
	public UserDto update(Long userId, UserDto userDto) {
		
		User userDb = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("user with Id: "+userId+" Not found"));
		BeanUtils.copyProperties(userDto, userDb, "credential"); // third one is ignore properties
		User updateUser = userRepository.save(userDb);
		return userMapper.toDto(updateUser);
	}

	@Override
	public void deleteByID(Long userId) {
		
		if(!userRepository.existsById(userId))
		{
			throw new UserNotFoundException("Cannot delete. user with id: "+userId+"does not exist.");
		}
		userRepository.deleteById(userId);
	}

	@Override
	public UserDto findByUsername(String username) {
		
		return userRepository.findByCredentialUsername(username).map(userMapper::toDto)
			.orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found."));
		
	}

	@Override
	public UserDto updateUser(Long userId, UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
