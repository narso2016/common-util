package com.libs.response.web;

import java.util.List;

public class ResponseList extends BaseResponse {
	private static final long serialVersionUID = 1L;
	
	private List<?> list;


	public ResponseList(List<?> list) {
		this.list = list;
		super.success=true;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}
