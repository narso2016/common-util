package com.libs.response.exception;

@SuppressWarnings("serial")
public class UnauthorizedAccessException extends RuntimeException {
	
	public UnauthorizedAccessException() {
		super();
	}
	
	public UnauthorizedAccessException(String message) {
		super(message);
	}
}
