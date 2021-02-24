package com.libs.util.geofence;

import java.io.Serializable;

public class GeofenceBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double latitude;
	protected double longitude;
	protected int accuracy;
	protected String provider;
	
	public GeofenceBean() {}

	public GeofenceBean(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public void setProvider(String provider){
		this.provider = provider;
	}
	public String getProvider(){
		return provider;
	}
}
