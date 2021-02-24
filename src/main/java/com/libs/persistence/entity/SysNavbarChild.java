package com.libs.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "SysNavbarChild")
@Table(name = "sys_navbar_child")
public class SysNavbarChild implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long idchild;
	
	private String link;
	private String name;
	private String glyph;
	private String idnavchild;
	private String title;
	private String icontype;
	private String classmenu;
	private boolean grouptitle;
	private String badge;
	private String badgeclass;
	private boolean collapse;
	private String icon;
	
	@JsonIgnore
	private Long sequenceorder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idparent")
	@JsonBackReference
	private SysNavbar sysNavbar;

	
	
	public String getIdnavchild() {
		return idnavchild;
	}

	public void setIdnavchild(String idnavchild) {
		this.idnavchild = idnavchild;
	}

	/**
	 * @return the idchild
	 */
	public long getIdchild() {
		return idchild;
	}

	/**
	 * @param idchild the idchild to set
	 */
	public void setIdchild(long idchild) {
		this.idchild = idchild;
	}

	/**
	 * @return the state
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param state the state to set
	 */
	public void setLink(String state) {
		this.link = state;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sequenceorder
	 */
	public Long getSequenceorder() {
		return sequenceorder;
	}

	/**
	 * @param sequenceorder the sequenceorder to set
	 */
	public void setSequenceorder(Long sequenceorder) {
		this.sequenceorder = sequenceorder;
	}

	/**
	 * @return the sysNavbar
	 */
	public SysNavbar getSysNavbar() {
		return sysNavbar;
	}

	/**
	 * @param sysNavbar the sysNavbar to set
	 */
	public void setSysNavbar(SysNavbar sysNavbar) {
		this.sysNavbar = sysNavbar;
	}
	

	/**
	 * @return the glyph
	 */
	public String getGlyph() {
		return glyph;
	}

	/**
	 * @param glyph the glyph to set
	 */
	public void setGlyph(String glyph) {
		this.glyph = glyph;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcontype() {
		return icontype;
	}

	public void setIcontype(String icontype) {
		this.icontype = icontype;
	}

	public String getClassmenu() {
		return classmenu;
	}

	public void setClassmenu(String classmenu) {
		this.classmenu = classmenu;
	}

	public boolean isGrouptitle() {
		return grouptitle;
	}

	public void setGrouptitle(boolean grouptitle) {
		this.grouptitle = grouptitle;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public String getBadgeclass() {
		return badgeclass;
	}

	public void setBadgeclass(String badgeclass) {
		this.badgeclass = badgeclass;
	}

	public boolean isCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	

}
