package com.libs.util.security;

import java.io.Serializable;
import java.util.Date;

public class SessionBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private Date expiredUser;
	private String pucuk;
	private AuthenticatedUser user;

	public SessionBean() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getExpiredUser() {
		return this.expiredUser;
	}

	public void setExpiredUser(Date expiredUser) {
		this.expiredUser = expiredUser;
	}
	
	public String getPucuk() {
		return pucuk;
	}

	public void setPucuk(String pucuk) {
		this.pucuk = pucuk;
	}

	public AuthenticatedUser getUser() {
		return user;
	}

	public void setUser(AuthenticatedUser user) {
		this.user = user;
	}
	
}
