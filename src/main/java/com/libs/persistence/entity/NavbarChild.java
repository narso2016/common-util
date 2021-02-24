package com.libs.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "NavbarChild")
@Table(name = "sys_navbar_child")
public class NavbarChild  implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long idchild;
	private String idparent;
	private String link;
	private String name;
	private String idnavchild;
	private String title;
	private String icontype;
	private String classmenu;
	private boolean grouptitle;
	private String badge;
	private String badgeclass;
	private boolean collapse;
	private String glyph;
	private String icon;
	private boolean active;
	private String path;
	private boolean isSelected;
	
	@JsonIgnore
	private Long sequenceorder;

	
	
	public String getIdparent() {
		return idparent;
	}

	public void setIdparent(String idparent) {
		this.idparent = idparent;
	}

	public String getIdnavchild() {
		return idnavchild;
	}

	public void setIdnavchild(String idnavchild) {
		this.idnavchild = idnavchild;
	}

	public long getIdchild() {
		return idchild;
	}

	public void setIdchild(long idchild) {
		this.idchild = idchild;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGlyph() {
		return glyph;
	}

	public void setGlyph(String glyph) {
		this.glyph = glyph;
	}

	public Long getSequenceorder() {
		return sequenceorder;
	}

	public void setSequenceorder(Long sequenceorder) {
		this.sequenceorder = sequenceorder;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	
	


}
