package com.libs.response.rest;

import com.libs.util.security.SessionBean;

public class JWTResponse {
	
	private SessionBean bean;
	
	private String redirectURL;

	public SessionBean getBean() {
		return bean;
	}

	public void setBean(SessionBean bean) {
		this.bean = bean;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	
	
}
