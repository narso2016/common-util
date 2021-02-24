package com.libs.util.geofence;

import java.io.IOException;
import java.net.MalformedURLException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.libs.util.exception.CoreException;
import com.libs.util.google.GoogleResponseResult;
import com.libs.util.google.GoogleResult;
import com.libs.util.http.HTTPResponseReader;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;
import com.libs.util.strings.Strings;

public class ReverseGeocode {
	public ReverseGeocode() {
	}

	private static ILogger logger = LogManager.getDefaultLogger();

	private static final String GEOCODE_URL = "http://maps.google.com/maps/api/geocode/json?latlng=[lat],[lng]&sensor=false";
	private static final String TAG_LATITUDE = "[lat]";
	private static final String TAG_LONGITUDE = "[lng]";

	private static final int ERROR_GEOCODE_UNKNOWN = -1;
	private static final int ERROR_GEOCODE_REQUEST = 301;
//	private static final int ERROR_GEOCODE_PARSE_RESPONSE = 302;
//	private static final String KEY_FORMATTED_ADDRESS = "formatted_address";

	private HTTPResponseReader httpResponseReader;

	public ReverseGeocode(HTTPResponseReader httpResponseReader) {
		this.httpResponseReader = httpResponseReader;
	}

	public String reverseGeocoding(double latitude, double longitude) throws CoreException {
		String addressResult = null;

		String jsonRequestUri = ReverseGeocode.buildReverseGeocodeRequestURL(latitude, longitude);
		if (logger != null)
			logger.log(ILogger.LEVEL_INFO, "Trying to reverse geocode through URI: " + jsonRequestUri);

		try {
			String jsonData = httpResponseReader.getResponseString(jsonRequestUri);

			Gson gson = new GsonBuilder().create();
			GoogleResponseResult result = gson.fromJson(jsonData, GoogleResponseResult.class);
			if (null != result) {
				GoogleResult[] results = result.getResults();
				if (null != results) {
					for (int i = 0; i < results.length; i++) {
						if (!Strings.isNullOrEmpty(results[i].getFormatted_address())) {
							addressResult = results[i].getFormatted_address();
							break;
						}	
					}
				}
			}
			if (logger != null)
				logger.log(ILogger.LEVEL_INFO, "Trying to reverse geocode through URI: " + jsonRequestUri
						+ " finished... Address found: " + addressResult);
		} catch (MalformedURLException me) {
			if (logger != null)
				logger.printStackTrace(me);
			else
				me.printStackTrace();

			throw new CoreException(ERROR_GEOCODE_REQUEST);
		} catch (IOException ioe) {
			if (logger != null)
				logger.printStackTrace(ioe);
			else
				ioe.printStackTrace();

			throw new CoreException(ERROR_GEOCODE_REQUEST);
		} catch (Exception ex) {
			if (logger != null)
				logger.printStackTrace(ex);
			else
				ex.printStackTrace();

			throw new CoreException(ERROR_GEOCODE_UNKNOWN);
		}

		return addressResult;
	}

	private static String buildReverseGeocodeRequestURL(double latitude, double longitude) {
		StringBuffer result = new StringBuffer(ReverseGeocode.GEOCODE_URL);
		ReverseGeocode.replace(result, TAG_LATITUDE, String.valueOf(latitude));
		ReverseGeocode.replace(result, TAG_LONGITUDE, String.valueOf(longitude));
		return result.toString();
	}

	private static void replace(StringBuffer message, String tag, String replacement) {
		int startTag = message.indexOf(tag);
		int endTag = startTag + tag.length();
		if (replacement != null && !"".equals(replacement.trim())) {
			message.replace(startTag, endTag, replacement);
		}
	}
}
