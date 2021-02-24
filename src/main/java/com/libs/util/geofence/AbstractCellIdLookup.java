package com.libs.util.geofence;

import java.text.DecimalFormat;
import java.text.ParseException;


public abstract class AbstractCellIdLookup {
	public abstract GeofenceBean getCoordinate(int MCC, int MNC, int LAC, int CID) throws Exception;
	
	public void formatCoordinatePrecision(GeofenceBean bean) {
		String format = "###.######";
		DecimalFormat df = new DecimalFormat(format);
		try {
			double lat = bean.getLatitude();
			lat = df.parse(df.format(lat)).doubleValue();
			double lng = bean.getLongitude();
			lng = df.parse(df.format(lng)).doubleValue();
			bean.setLatitude(lat);
			bean.setLongitude(lng);
		}
		catch (ParseException pe) {
			pe.printStackTrace();
		}
	}
}
