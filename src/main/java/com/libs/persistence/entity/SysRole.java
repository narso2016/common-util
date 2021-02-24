package com.libs.persistence.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "SysRole")
@Table(name = "sys_role")
public class SysRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="role_id")
	private String roleId;

	@Column(name="role_name")
	private String roleName;
	
	private String description;

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

//	@OneToMany(mappedBy = "sysRole")
//	@JsonManagedReference
//	@JsonIgnore
//	private List<SysUserRole> sysUsers;	

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="sys_role_service", 
		joinColumns={@JoinColumn(name="role_id")}, 
		inverseJoinColumns= {@JoinColumn(name="service_code")})
	@WhereJoinTable(clause="active=true")
	@Where(clause="active=true")
	@JsonIgnore
	private List<SysService> sysServices;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="sys_privilege", 
		joinColumns={@JoinColumn(name="role_id")}, 
		inverseJoinColumns= {@JoinColumn(name="idnavbar")})
	@WhereJoinTable(clause="active=true")
	@Where(clause="active=true")
	@JsonIgnore
	private List<SysNavbar> sysNavbars;

	public SysRole() {
		super();
	}

	public String getRoleId() {
		return roleId;
	}



	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SysService> getSysServices() {
		return sysServices;
	}

	public void setSysServices(List<SysService> sysServices) {
		this.sysServices = sysServices;
	}

//	public List<SysUserRole> getSysUsers() {
//		return sysUsers;
//	}
//
//	public void setSysUsers(List<SysUserRole> sysUsers) {
//		this.sysUsers = sysUsers;
//	}

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
	

	/**
	 * @return the sysNavbar
	 */
	public List<SysNavbar> getSysNavbars() {
		return sysNavbars;
	}

	/**
	 * @param sysNavbar the sysNavbar to set
	 */
	public void setSysNavbars(List<SysNavbar> sysNavbars) {
		this.sysNavbars = sysNavbars;
	}
}
