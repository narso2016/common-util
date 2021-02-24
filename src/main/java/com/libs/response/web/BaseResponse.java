package com.libs.response.web;

import java.io.Serializable;

public class BaseResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//default base Response
	protected boolean success = true;
	protected String message;

	public BaseResponse() {
	}

	public BaseResponse(boolean success) {
		super();
		this.success = success;
	}

	public BaseResponse(String message) {
		super();
		this.message = message;
	}

	public BaseResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
