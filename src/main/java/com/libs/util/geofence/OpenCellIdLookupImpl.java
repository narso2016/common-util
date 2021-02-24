package com.libs.util.geofence;

import com.libs.util.http.HTTPResponseReader;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;
import com.libs.util.tools.Tools;

public class OpenCellIdLookupImpl extends AbstractCellIdLookup {
	private static ILogger logger = LogManager.getDefaultLogger();

	public static final String TAG_CELLID = "[cid]";
	public static final String TAG_MCC = "[mcc]";
	public static final String TAG_MNC = "[mnc]";
	public static final String TAG_LAC = "[lac]";
	public static final String TAG_FORMAT = "[format]";
	public static final String FORMAT_TXT = "txt";
	public static final String FORMAT_XML = "xml";
	public static final String FORMAT_JSON = "json";

	private String format;
	private final static String OPENCELLID_SERVICE_URI = "http://www.opencellid.org/cell/get?cellid=[cid]&mcc=[mcc]&mnc=[mnc]&lac=[lac]&fmt=[format]";

	public OpenCellIdLookupImpl() {
	}

	public OpenCellIdLookupImpl(String format) {
		this.format = format;
	}

	public GeofenceBean getCoordinate(int MCC, int MNC, int LAC, int CID)
			throws Exception {
		if (format == null)
			format = FORMAT_TXT;

		return this.processCellId(MCC, MNC, LAC, CID);
	}

	public GeofenceBean processCellId(int MCC, int MNC, int LAC, int CID)
			throws Exception {

		GeofenceBean result = null;
		try {
			HTTPResponseReader httpResponseReader = new HTTPResponseReader();
			final String baseURL = this.buildRequestURL(MCC, MNC, LAC, CID);

			if (logger != null) {
				logger.log(ILogger.LEVEL_INFO, "Trying connect to " + baseURL
						+ " to get lat/lon of " + "MCC:" + MCC + "|MNC:" + MNC
						+ "|LAC:" + LAC + "|CID:" + CID);
			} else {
				System.out.println("Trying connect to " + baseURL
						+ " to get lat/lon of " + "MCC:" + MCC + "|MNC:" + MNC
						+ "|LAC:" + LAC + "|CID:" + CID);
			}

			String resultData = httpResponseReader.getResponseString(baseURL);
			result = this.processData(resultData);
			double lat = result.getLatitude();
			double lng = result.getLongitude();
			if ((lat == 0d && lng == 0d)) {
				result = null;
				if (logger != null) {
					logger.log(ILogger.LEVEL_WARNING,
							"Cannot retrieve coordinate from " + baseURL + " "
									+ "|MCC:" + MCC + "|MNC:" + MNC + "|LAC:"
									+ LAC + "|CID:" + CID);
				} else {
					System.out.println("Cannot retrieve coordinate from "
							+ baseURL + " " + "|MCC:" + MCC + "|MNC:" + MNC
							+ "|LAC:" + LAC + "|CID:" + CID);
				}
			} else {
				this.formatCoordinatePrecision(result);
				if (logger != null) {
					logger.log(ILogger.LEVEL_INFO,
							"Coordinate retrieved: " + result.getLatitude()
									+ "," + result.getLongitude() + "; from "
									+ baseURL);
				} else {
					System.out.println("Coordinate retrieved: "
							+ result.getLatitude() + ","
							+ result.getLongitude() + "; from " + baseURL);
				}
			}
		} catch (Exception ex) {
			throw ex;
		}

		return result;
	}

	private GeofenceBean processData(String data) {
		GeofenceBean result = null;
		if (FORMAT_TXT.equals(format)) {
			result = this.processTxtData(data);
		} else if (FORMAT_JSON.equals(format)) {
		} else if (FORMAT_XML.equals(format)) {
		} else {
			result = new GeofenceBean();
		}
		return result;
	}

	private GeofenceBean processTxtData(String data) {
		GeofenceBean bean = new GeofenceBean();
		String[] splittedData = Tools.split(data, ",");
		bean.setLatitude(Double.parseDouble(splittedData[0]));
		bean.setLongitude(Double.parseDouble(splittedData[1]));
		bean.setAccuracy(Integer.parseInt(splittedData[2]));
		return bean;
	}

	private String buildRequestURL(int MCC, int MNC, int LAC, int CID) {
		StringBuffer result = new StringBuffer(OPENCELLID_SERVICE_URI);
		this.replace(result, TAG_MCC, String.valueOf(MCC));
		this.replace(result, TAG_MNC, String.valueOf(MNC));
		this.replace(result, TAG_LAC, String.valueOf(LAC));
		this.replace(result, TAG_CELLID, String.valueOf(CID));
		this.replace(result, TAG_FORMAT, format);
		return result.toString();
	}

	private void replace(StringBuffer message, String tag, String replacement) {
		int startTag = message.indexOf(tag);
		int endTag = startTag + tag.length();
		if (replacement != null && !"".equals(replacement.trim())) {
			message.replace(startTag, endTag, replacement);
		}
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
