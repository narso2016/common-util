package com.libs.response.web;

public class ResponseSession<T> extends BaseResponse {
	private static final long serialVersionUID = 1L;
	T data;	
	
	public ResponseSession() {}
	
	public ResponseSession(T data) {
		super();
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}
