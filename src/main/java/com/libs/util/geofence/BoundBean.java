package com.libs.util.geofence;

public class BoundBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Double southBound;
	private Double westBound;
	private Double northBound;
	private Double eastBound;

	public Double getSouthBound() {
		return southBound;
	}

	public void setSouthBound(Double southBound) {
		this.southBound = southBound;
	}

	public Double getWestBound() {
		return westBound;
	}

	public void setWestBound(Double westBound) {
		this.westBound = westBound;
	}

	public Double getNorthBound() {
		return northBound;
	}

	public void setNorthBound(Double northBound) {
		this.northBound = northBound;
	}

	public Double getEastBound() {
		return eastBound;
	}

	public void setEastBound(Double eastBound) {
		this.eastBound = eastBound;
	}
}