package com.libs.util.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public abstract class GeneralLayerBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String created_by;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date created_date;
	
	private String modified_by;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date modified_date;
	
	private String appr_by;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date appr_date;
	
	private boolean approved;

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}

	public String getAppr_by() {
		return appr_by;
	}

	public void setAppr_by(String appr_by) {
		this.appr_by = appr_by;
	}

	public Date getAppr_date() {
		return appr_date;
	}

	public void setAppr_date(Date appr_date) {
		this.appr_date = appr_date;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
