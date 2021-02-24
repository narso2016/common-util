package com.libs.response.web;

public class BadStatus extends BaseResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;


	public BadStatus(String message) {
		super.success=false;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
