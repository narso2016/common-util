package com.libs.util.auditrail;

public class AuditResponse<T,U> {

	private T oldVal;
	private U newVal;
	
	public AuditResponse(T oldVAl, U newVal) {
		this.oldVal = oldVAl;
		this.newVal = newVal;
	}

	public T getOldVal() {
		return oldVal;
	}

	public void setOldVal(T oldVal) {
		this.oldVal = oldVal;
	}

	public U getNewVal() {
		return newVal;
	}

	public void setNewVal(U newVal) {
		this.newVal = newVal;
	}
	
	
	
}
