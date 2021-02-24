package com.libs.response.rest;

import com.libs.response.CommonResponseConstant;
import com.libs.response.service.ResponseMapping;

public class CommonResponse<T> extends CommonStatus{
	T data;
	
	String redirectUrl;
	
	String jwtToken;

	public CommonResponse(){
		super(200, CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC);
	}

	public CommonResponse(ResponseMapping responseMapping){
		super(200, CommonResponseConstant.DEFAULT_SUCCESS_CODE, responseMapping.getResponseMap().get(CommonResponseConstant.DEFAULT_SUCCESS_CODE));
	}
	
	public CommonResponse(CommonStatus commonStatus) {
		super(commonStatus.getStatus(), commonStatus.getCode(), commonStatus.getMessage());
	}

	public CommonResponse(T data) {
		this();
		this.data = data;
	}
	
	public CommonResponse(T data, String redirectUrl) {
		this();
		this.data = data;
		this.redirectUrl = redirectUrl;
	}
	

	public CommonResponse(T data, boolean isToken, String jwtToken) {
		this();
		this.data = data;
		this.jwtToken = jwtToken;
	}
	
	public CommonResponse(T data, ResponseMapping responseMapping){
		this(responseMapping);
		this.data = data;
	}
	
	public CommonResponse(T data, ResponseMapping responseMapping, String redirectUrl){
		this(responseMapping);
		this.data = data;
		this.redirectUrl = redirectUrl;
	}

	public CommonResponse(T data, ResponseMapping responseMapping, boolean isToken, String jwtToken) {
		this();
		this.data = data;
		this.jwtToken = jwtToken;
	}


	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}


	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}
