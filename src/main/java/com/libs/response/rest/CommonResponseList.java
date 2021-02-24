package com.libs.response.rest;

import java.util.List;
import com.libs.response.CommonResponseConstant;
import com.libs.response.service.ResponseMapping;

public class CommonResponseList<T> extends CommonStatus{
	List<T> data;
	
	public CommonResponseList() {
		super(200, CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC);
	}
	
	public CommonResponseList(int status) {
		super(status, CommonResponseConstant.GENERAL_ERROR_CODE, CommonResponseConstant.GENERAL_ERROR_DESC);
	}

	public CommonResponseList(CommonStatus commonStatus) {
		super(commonStatus.getStatus(), commonStatus.getCode(), commonStatus.getMessage());
	}

	public CommonResponseList(List<T> data) {
		this();
		this.data = data;
	}
	

	public CommonResponseList(ResponseMapping responseMapping) {
		super(200, CommonResponseConstant.DEFAULT_SUCCESS_CODE, responseMapping.getResponseMap().get(CommonResponseConstant.DEFAULT_SUCCESS_CODE));
	}

	public CommonResponseList(List<T> data, ResponseMapping responseMapping) {
		this(responseMapping);
		this.data = data;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
