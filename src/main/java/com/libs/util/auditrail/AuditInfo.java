package com.libs.util.auditrail;

public class AuditInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String auditTableName;
	private String[] auditPrimaryKeyFields;
	private String[] auditFields;
	private String[] auditDbFields;
	private String[] auditDBPrimaryKeyFields;

	public String getAuditTableName() {
		return auditTableName;
	}

	public void setAuditFields(String[] auditFields) {
		this.auditFields = auditFields;
	}

	public void setAuditTableName(String auditTableName) {
		this.auditTableName = auditTableName;
	}

	public void setAuditPrimaryKeyFields(String[] auditPrimaryKeyFields) {
		this.auditPrimaryKeyFields = auditPrimaryKeyFields;
	}

	public void setAuditDbFields(String[] auditDbFields) {
		this.auditDbFields = auditDbFields;
	}

	public String[] getAuditFields() {
		return auditFields;
	}

	public String[] getAuditPrimaryKeyFields() {
		return auditPrimaryKeyFields;
	}

	public String[] getAuditDBPrimaryKeyFields() {
		return auditDBPrimaryKeyFields;
	}

	public void setAuditDBPrimaryKeyFields(String[] auditDBPrimaryKeyFields) {
		this.auditDBPrimaryKeyFields = auditDBPrimaryKeyFields;
	}

	public String[] getAuditDbFields() {
		return auditDbFields;
	}

	public AuditInfo() {
	}

	public AuditInfo(Auditable auditTable) {
		this.auditFields = auditTable.getAuditFields();
		this.auditDbFields = auditTable.getAuditDBFields();
		this.auditPrimaryKeyFields = auditTable.getAuditPrimaryKeyFields();
		this.auditTableName = auditTable.getAuditTableName();
		this.setAuditDBPrimaryKeyFields(auditTable.getAuditDBPrimaryKeyFields());
	}

}
