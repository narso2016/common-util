package com.libs.util.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Lob;

public abstract class DetailUserBean extends EdTimeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	
	private String phone_no;
	private String hp_no;
	
	private String image_path;
	@Lob
	@Column(length = 100000)
	private byte[] image_blob;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getHp_no() {
		return hp_no;
	}
	public void setHp_no(String hp_no) {
		this.hp_no = hp_no;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public byte[] getImage_blob() {
		return image_blob;
	}
	public void setImage_blob(byte[] image_blob) {
		this.image_blob = image_blob;
	}
	
	
}
