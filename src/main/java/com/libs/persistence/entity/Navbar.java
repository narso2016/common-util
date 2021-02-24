package com.libs.persistence.entity;

import java.io.Serializable;
import java.util.List;

public class Navbar  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
	private boolean active;
	private long seqorder;	
	private String path;
    private boolean selected;
	private boolean closed;
	private List<NavbarChild> children;

	public String getIdnavbar() {
		return idnavbar;
	}

	public void setIdnavbar(String idnavbar) {
		this.idnavbar = idnavbar;
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

	public boolean isCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

	public String getGlyph() {
		return glyph;
	}

	public void setGlyph(String glyph) {
		this.glyph = glyph;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getSeqorder() {
		return seqorder;
	}

	public void setSeqorder(long seqorder) {
		this.seqorder = seqorder;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<NavbarChild> getChildren() {
		return children;
	}

	public void setChildren(List<NavbarChild> children) {
		this.children = children;
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
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}


	
	

}
