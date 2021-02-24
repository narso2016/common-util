package com.libs.util.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public abstract class EdTimeBean extends GeneralLayerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean dormant;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expired_date;

	public boolean isDormant() {
		return dormant;
	}

	public void setDormant(boolean dormant) {
		this.dormant = dormant;
	}

	public Date getExpired_date() {
		return expired_date;
	}

	public void setExpired_date(Date expired_date) {
		this.expired_date = expired_date;
	}

}
