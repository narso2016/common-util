package com.libs.response.web;

public class ResponseLogin extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private String userName;

	public ResponseLogin() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
