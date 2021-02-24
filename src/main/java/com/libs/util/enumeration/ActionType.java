package com.libs.util.enumeration;

public enum ActionType {
	ADD_ACTION("INSERT"), EDIT_ACTION("UPDATE"), DELETE_ACTION("DELETE"), APPROVE_ACTION("APPROVE"), 
	REJECT_ACTION("REJECT"), APPROVE_ACTION_LINK("1"), REJECT_ACTION_LINK("0");
	
	private final String action;
	
	ActionType(String action){
		this.action = action;
	}
	
	public String action() {
		return this.action;
	}
	
	@Override
	public String toString() {
		return this.action;
	}
}
