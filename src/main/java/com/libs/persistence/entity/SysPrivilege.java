package com.libs.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "SysPrivilege")
@Table(name = "sys_privilege")
public class SysPrivilege implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="privilegeid")
	private int privilegeid;
	
	@Column(name="role_id")
	private String roleId;
	
	@Column(name="idnavbar")
	private String idnavbar;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="childname")
	private String childname;
	
	public SysPrivilege() {
		super();
	}

	public int getPrivilegeid() {
		return privilegeid;
	}

	public void setPrivilegeid(int privilegeid) {
		this.privilegeid = privilegeid;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getIdnavbar() {
		return idnavbar;
	}

	public void setIdnavbar(String idnavbar) {
		this.idnavbar = idnavbar;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}
	
	

}
