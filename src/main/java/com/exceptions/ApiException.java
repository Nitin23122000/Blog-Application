package com.exceptions;

import org.apache.coyote.BadRequestException;

public class ApiException extends RuntimeException {

	
	public ApiException(String message) {
		super(message);
		
	}
	
	public ApiException() {
		super();
		
	}
	
	
	
	
}
