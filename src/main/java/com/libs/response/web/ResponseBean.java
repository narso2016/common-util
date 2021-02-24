package com.libs.response.web;

public class ResponseBean extends BaseResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object bean;

	public ResponseBean(Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}
	
	

}
