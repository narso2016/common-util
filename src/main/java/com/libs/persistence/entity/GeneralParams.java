package com.libs.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libs.util.auditrail.Auditable;

@Entity(name = "GeneralParams")
@Table(name = "general_param")
public class GeneralParams implements Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private static final String auditTableName = "general_param";
	@JsonIgnore
	private static final String[] auditPrimaryKeyFields = {"paramId"};
	@JsonIgnore
	private static final String[] auditDBPrimaryKeyFields = {"param_id"};
	@JsonIgnore
	private static final String[] auditFields = {"paramId", "paramName", "paramValue", "paramType"};
	@JsonIgnore
	private static final String[] auditDBFields = {"param_id", "param_name", "param_value", "param_type"};
	
	
	@Id
	@Column(name="param_id")
	private String paramId;
	
	@Column(name="param_name")
	private String paramName;
	
	@Column(name="param_value")
	private String paramValue;
	
	@Column(name="changed_by")
	private String changedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="changed_date")
	private Date changedDate;
	
	@Column(name="approve_by")
	private String approveBy;
	
	@Column(name="approved")
	private Boolean approved;
	
	@Column(name="action")
	private String action;

	@Column(name="param_type")
	private String paramType;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="approve_date")
	private Date approveDate;
	
	public GeneralParams() {
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Date getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Override
	public String getAuditTableName() {
		return auditTableName;
	}

	@Override
	public String[] getAuditPrimaryKeyFields() {
		return auditPrimaryKeyFields;
	}

	@Override
	public String[] getAuditFields() {
		return auditFields;
	}

	@Override
	public String[] getAuditDBFields() {
		return auditDBFields;
	}

	@Override
	public String[] getAuditDBPrimaryKeyFields() {
		return auditDBPrimaryKeyFields;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}


}
