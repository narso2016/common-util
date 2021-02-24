package com.libs.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "SysBranch")
@Table(name = "sys_branch")
public class SysBranch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="branch_code")
	private String branchcode;
	
	private String branch_parent;
	private String branch_name;
	private String branch_addr;
	
	@Column(name="is_dki")
	private int isdki;
	
	private String chain;
	
	@Column(name="is_charged_fee")
	private String isChargedFee;
	
	
	public SysBranch() {
	}
	
	@Override
	public String toString() {
		return this.branchcode+" - "+this.branch_name;
	}
	
	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getBranch_parent() {
		return branch_parent;
	}

	public void setBranch_parent(String branch_parent) {
		this.branch_parent = branch_parent;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getBranch_addr() {
		return branch_addr;
	}

	public int getIsdki() {
		return isdki;
	}

	public void setIsdki(int isdki) {
		this.isdki = isdki;
	}

	public void setBranch_addr(String branch_addr) {
		this.branch_addr = branch_addr;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getIsChargedFee() {
		return isChargedFee;
	}

	public void setIsChargedFee(String isChargedFee) {
		this.isChargedFee = isChargedFee;
	}
	
	
	
	
}
