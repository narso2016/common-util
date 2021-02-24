package com.libs.response.web;

import java.util.List;

import com.libs.response.web.page.SearchResult;

public class ResponsePage extends BaseResponse {

	private static final long serialVersionUID = 1L;
	private int totalRows;
	private List<?> list;

	public ResponsePage(SearchResult searchResult) {
		super(true);
		list = searchResult.getRecs();
		totalRows = searchResult.getPagingInfo().getTotalRows();
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}
