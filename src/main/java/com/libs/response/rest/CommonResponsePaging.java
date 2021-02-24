package com.libs.response.rest;

import com.libs.response.CommonResponseConstant;
import com.libs.response.service.ResponseMapping;

public class CommonResponsePaging<T> extends CommonStatus{
	CommonPaging<T> page;

	public CommonResponsePaging() {
		super(200, CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC);
	}

	public CommonResponsePaging(ResponseMapping responseMapping) {
		super(200, CommonResponseConstant.DEFAULT_SUCCESS_CODE, responseMapping.getResponseMap().get(CommonResponseConstant.DEFAULT_SUCCESS_CODE));
	}

	
	public CommonResponsePaging(CommonStatus commonStatus) {
		super(commonStatus.getStatus(), commonStatus.getCode(), commonStatus.getMessage());
	}

	public CommonPaging<T> getPaging() {
		return page;
	}

	 public CommonResponsePaging(CommonPaging<T> page) {
		 this();
		 this.page = page;
	 }
	 
	 public CommonResponsePaging(CommonPaging<T> page, ResponseMapping responseMapping){
		 this(responseMapping);
		 this.page = page;
	 }

	public void setPage(CommonPaging<T> page) {
		this.page = page;
	}

}
