package com.libs.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "SysNavbar")
@Table(name = "sys_navbar")
public class SysNavbar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	private String idnavbar;
	private String link;
	private String name;
	private String title;
	private String icontype;
	private String classmenu;
	private boolean grouptitle;
	private String badge;
	private String badgeclass;
	private boolean collapse;
	private String glyph;
	private String icon;
	private String path;
	private boolean isSelected;
	private boolean isClosed;
	
	@JsonIgnore
	private long seqorder;	

	@JsonIgnore
	private boolean active;
	
	@OneToMany(mappedBy="sysNavbar")
	@JsonManagedReference
	private List<SysNavbarChild> children;
	

	/**
	 * @return the idnavbar
	 */
	public String getIdnavbar() {
		return idnavbar;
	}

	/**
	 * @param idnavbar the idnavbar to set
	 */
	public void setIdnavbar(String idnavbar) {
		this.idnavbar = idnavbar;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
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
	 * @return the collapse
	 */
	public boolean isCollapse() {
		return collapse;
	}

	/**
	 * @param collapse the collapse to set
	 */
	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the seqorder
	 */
	public long getSeqorder() {
		return seqorder;
	}

	/**
	 * @param seqorder the seqorder to set
	 */
	public void setSeqorder(long seqorder) {
		this.seqorder = seqorder;
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

	/**
	 * @return the children
	 */
	public List<SysNavbarChild> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<SysNavbarChild> children) {
		this.children = children;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	

	
//
//	/**
//	 * @return the sysRoles
//	 */
//	public List<SysRole> getSysRoles() {
//		return sysRoles;
//	}
//
//	/**
//	 * @param sysRoles the sysRoles to set
//	 */
//	public void setSysRoles(List<SysRole> sysRoles) {
//		this.sysRoles = sysRoles;
//	}
//	
}
