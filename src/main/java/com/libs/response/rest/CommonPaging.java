package com.libs.response.rest;

import java.util.List;

import com.libs.response.service.ResponseMapping;

public class CommonPaging<T>{
	List<T> data;

	int page;
	int rowPerPage;
	int totalData;

	public CommonPaging() {
	}
	

	public CommonPaging(ResponseMapping responseMapping) {
	}

	public List<T> getData() {
		return data;
	}

	public int getPage() {
		return page;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public int getStartRow() {
		return page * rowPerPage;
	}

	public int getTotalData() {
		return totalData;
	}

	public int getTotalPage() {
		return ((totalData-1)/rowPerPage) + 1;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}

}
