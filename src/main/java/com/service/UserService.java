package com.service;

import java.util.List;

import com.dto.UserDto;
import com.payloads.ApiResponse;

public interface UserService {

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto,Long userid);
	
	UserDto getUserById(Long userid);
	
	List<UserDto> getAllUser();
	
	ApiResponse deleteUser(Long userid);
}
