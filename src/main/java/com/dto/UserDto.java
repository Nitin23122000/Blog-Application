package com.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;


import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserDto {

	private Long uid;
	
	@NotEmpty
	@Size(min = 4,max = 8,message = "Name Must Contain min 4 chars and max 8 chars")
	private String username;
	
	@NotEmpty
	@Size(min = 3,max = 6,message = "password must contain min 3 chars and 6 chars")
	private String password;
	
	private String mobileno;
	
	private String address;
	
//	private List<UserDto> users;
	
	
	
}

