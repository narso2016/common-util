package com.libs.util.auditrail;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Auditable extends java.io.Serializable {
	@JsonIgnore
	public String getAuditTableName();
	@JsonIgnore
	public String[] getAuditDBPrimaryKeyFields();
	@JsonIgnore
	public String[] getAuditPrimaryKeyFields();
	@JsonIgnore
	public String[] getAuditFields();
	@JsonIgnore
	public String[] getAuditDBFields();
}
