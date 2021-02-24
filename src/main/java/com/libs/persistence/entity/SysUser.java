package com.libs.persistence.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libs.util.auditrail.Auditable;

@Entity(name = "SysUser")
@Table(name = "sys_user")
public class SysUser implements Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@JsonIgnore
	private static final String auditTableName = "sys_user";
	@JsonIgnore
	private static final String[] auditPrimaryKeyFields = {"userName"};
	@JsonIgnore
	private static final String[] auditDBPrimaryKeyFields = {"user_name"};
	@JsonIgnore
	private static final String[] auditFields = {"userName", "name", "password", "email", "phone_no", "hp_no", "sysBranch.branchcode"};
	@JsonIgnore
	private static final String[] auditDBFields = {"user_name", "name", "password", "email", "phone_no", "hp_no", "branch_code"};
	
	
	@Id
	@Column(name = "user_name")
	private String userName;

	private String name;
	
	@JsonIgnore
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	private Date last_login;
	
	private boolean logedin;
	
	private boolean is_user_sso;

	private boolean locked;
	private int locked_count;
	
	@Column(name = "is_dki")
	private int is_dki;

	private String email;

	private String phone_no;
	private String hp_no;
	
	@Column(name="branch_code")
	private String branchcode;

	/*@Lob
	@Column(length = 100000)
	private byte[] image_blob;
	private String image_path;*/

	private boolean dormant;

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

	@Temporal(TemporalType.TIMESTAMP)
	private Date expired_date;

	@Temporal(TemporalType.TIMESTAMP)
	private Date locked_date;
	
	@Column(name="role_name")
	private String roleName;
	
	@Column(name="role_id")
	private String roleId;
	
	@Column(name="action")
	private String action;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_code", insertable = false, updatable = false)
	private SysBranch sysBranch;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="sys_user_role", 
		joinColumns={@JoinColumn(name="user_name")}, 
		inverseJoinColumns= {@JoinColumn(name="role_id")})
//	@WhereJoinTable(clause="active=1")
//	@Where(clause="active=1")
	@JsonIgnore
	private List<SysRole> sysRoles;
	
//	@OneToMany(mappedBy = "sysUser")
//	@JsonManagedReference
//	private List<SysUserRole> sysRole;

	public SysUser() {
	}
	
	

	public SysUser(String userName, String name, String password, SysBranch sysBranch, List<SysRole> sysRoles) {
		super();
		this.userName = userName;
		this.name = name;
		this.password = password;
		this.logedin = false;
		this.locked = false;
		this.locked_count = 0;
		this.dormant = false;
		this.created_by = "SSO";
		this.created_date = new Date();
		this.appr_by = "SSO";
		this.appr_date = new Date();
		this.approved = true;
		this.sysBranch = sysBranch;
		this.sysRoles = sysRoles;
		this.created_by = "SSO";
		this.created_date = new Date();
		this.action = "ADD";
		this.is_user_sso = true;
	}



	@Override
	public String toString() {
		return this.userName + " - " + this.name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public boolean isLogedin() {
		return logedin;
	}

	public void setLogedin(boolean logedin) {
		this.logedin = logedin;
	}

	public SysBranch getSysBranch() {
		return sysBranch;
	}

	public void setSysBranch(SysBranch sysBranch) {
		this.sysBranch = sysBranch;
	}

	public List<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getLocked_count() {
		return locked_count;
	}

	public void setLocked_count(int locked_count) {
		this.locked_count = locked_count;
	}

	public Date getLocked_date() {
		return locked_date;
	}

	public void setLocked_date(Date locked_date) {
		this.locked_date = locked_date;
	}

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

	/*public String getImage_path() {
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
	}*/

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


	public String getBranchcode() {
		return branchcode;
	}



	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public String getRoleId() {
		return roleId;
	}



	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
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



	public boolean isIs_user_sso() {
		return is_user_sso;
	}



	public void setIs_user_sso(boolean is_user_sso) {
		this.is_user_sso = is_user_sso;
	}



	public int getIs_dki() {
		return is_dki;
	}



	public void setIs_dki(int is_dki) {
		this.is_dki = is_dki;
	}


	
	
}
