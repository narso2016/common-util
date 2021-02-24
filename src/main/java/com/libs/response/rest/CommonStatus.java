package com.libs.response.rest;

import com.libs.response.service.ResponseMapping;

public class CommonStatus {
	int status;
	String code;
	String message;
	
	public CommonStatus(int status) {
		this.status = status;
	}

	public CommonStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public CommonStatus(int status, String code, String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
	
	public CommonStatus(int status,  String code, ResponseMapping responseMapping) {
		this.status = status;
		this.code = code;
		this.message = responseMapping.getResponseMap().get(code);
	}
	
	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
