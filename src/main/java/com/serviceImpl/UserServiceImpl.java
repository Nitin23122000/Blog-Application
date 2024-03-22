package com.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.UserDto;
import com.entities.User;
import com.exceptions.ResourceNotFoundException;
import com.payloads.ApiResponse;
import com.repository.UserRepository;
import com.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl  implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public UserDto createUser(UserDto userDto) {

		//converting dto object into original entity object
		User user = this.modelMapper.map(userDto, User.class);
		
		User u = this.userRepository.save(user);
		log.info("UserServiceImpl.class","createUser()","User Created Successfully",userDto.toString());
		return this.modelMapper.map(u, UserDto.class);
	}


	@Override
	public UserDto updateUser(UserDto userDto, Long userid) {

		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userid));		
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setMobileno(userDto.getMobileno());
		user.setAddress(userDto.getAddress());
		
		User updateUser = this.userRepository.save(user);
		 return this.modelMapper.map(updateUser, UserDto.class);
	}


	@Override
	public UserDto getUserById(Long userid) {

		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userid));
		return this.modelMapper.map(user, UserDto.class);
	
	}


	@Override
	public List<UserDto> getAllUser() {

		List<User> users = this.userRepository.findAll();
		
		List<UserDto> userDtos = users.stream().map((u)-> this.modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
		return userDtos;
	}


	@Override
	public ApiResponse deleteUser(Long userid) {
		
		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("user","user id", userid));
		this.userRepository.delete(user);
		ApiResponse response = new ApiResponse("User Deleted Successfully", true);
		return response;
	}

}
