package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.UserDto;
import com.payloads.ApiResponse;
import com.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog")
public class UserController {

	@Autowired
	private UserService userService; 
	
	@PostMapping("/createUser")
	private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		if(userDto.equals(null)) return new ResponseEntity<>(userDto,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.userService.createUser(userDto),HttpStatus.OK);
		
	}
	
	//update user (we can do with save or update method of jpa repository)
	@PutMapping("/updateUser/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto,@PathVariable("userid") Long userid ){
		if(userdto.equals(null)) return new ResponseEntity<>(userdto,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.userService.updateUser(userdto,userid),HttpStatus.OK);
	}
	
	@GetMapping("/getUser/{userid}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userid") Long userid){
		if(!(userid >0)) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(this.userService.getUserById(userid),HttpStatus.OK);
	}
	
	@GetMapping("/getAllUser")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return new ResponseEntity<>(this.userService.getAllUser(),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser/{userid}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userid") Long userid){
		if(!(userid >0)) return new ResponseEntity<>(new ApiResponse("Please Enter Valid User id", false),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.userService.deleteUser(userid),HttpStatus.OK);
	}
}
