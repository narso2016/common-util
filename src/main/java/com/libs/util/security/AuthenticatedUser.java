package com.libs.util.security;

import java.util.List;

public class AuthenticatedUser {
	String userName;
	String name;
	String roleName;
	String authenticatedMethod;
	String branchCode;
	String password;
	
	String branchName;

	List<String> services;
	List<String> roles;
	List<String> navbars;
	
	public AuthenticatedUser() {}
	
	public AuthenticatedUser(AuthenticatedUser source, AuthenticatedUser target) {
		this.userName = source.userName;
		this.name = source.getName();
		this.roleName = source.getRoleName();
		this.authenticatedMethod = source.getAuthenticatedMethod();
		this.services = target.getServices();
		this.roles = target.getRoles();
		this.navbars = target.getNavbars();
		this.branchCode = target.getBranchCode();
		this.branchName = target.getBranchName();
		this.password = source.getPassword();
	}

	public String getAuthenticatedMethod() {
		return authenticatedMethod;
	}

	public List<String> getServices() {
		return services;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}


	public void setAuthenticatedMethod(String authenticatedMethod) {
		this.authenticatedMethod = authenticatedMethod;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public List<String> getRoles(){
		return this.roles;
	}
	
	public void setNavbars(List<String> navbars) {
		this.navbars = navbars;
	}
	
	public List<String> getNavbars(){
		return this.navbars;
	}
	
	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

	
}
