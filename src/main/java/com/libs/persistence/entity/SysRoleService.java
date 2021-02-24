package com.libs.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "SysRoleService")
@Table(name = "sys_role_service")
public class SysRoleService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long role_service_id;
	
	@Column(name="role_id")
	private String roleid;
	
	@Column(name="service_code")
	private String servicecode;
	
	private boolean active;

	/**
	 * @return the role_service_id
	 */
	public long getRole_service_id() {
		return role_service_id;
	}

	/**
	 * @param role_service_id the role_service_id to set
	 */
	public void setRole_service_id(long role_service_id) {
		this.role_service_id = role_service_id;
	}

	/**
	 * @return the roleid
	 */
	public String getRoleid() {
		return roleid;
	}

	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	/**
	 * @return the servicecode
	 */
	public String getServicecode() {
		return servicecode;
	}

	/**
	 * @param servicecode the servicecode to set
	 */
	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
