package com.libs.response.web.page;

import java.util.List;

public class DataTables {

	private Integer sEcho;
	
	private String sSearch;
	
	private Integer iDisplayStart;
	private Integer iDisplayLength;
	
	private Integer iTotalRecords;
	private Integer iTotalDisplayRecords;
	
	private List<?> aaData;
	
	public void extract(SearchResult searchResult) {
		if (searchResult.getPagingInfo() == null) {
			this.iTotalRecords = this.iTotalDisplayRecords = searchResult.getRecs().size();
		} else {
			this.iTotalRecords = this.iTotalDisplayRecords = searchResult.getPagingInfo().getTotalRows();
		}
		this.aaData = searchResult.getRecs();
	}
	
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	public Integer getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public Integer getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public Integer getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<?> getAaData() {
		return aaData;
	}
	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}
	
}
