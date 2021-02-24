package com.libs.response.web;

public class ResponsePrivilege extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private boolean authorized;

	public ResponsePrivilege(boolean authorized) {
		this.authorized = authorized;
	}

	public ResponsePrivilege() {

	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}
