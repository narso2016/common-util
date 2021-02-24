package com.libs.util.geofence;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;

public class GoogleCellIdLookupImpl extends AbstractCellIdLookup {
	private static ILogger logger = LogManager.getDefaultLogger();

	public GeofenceBean getCoordinate(int MCC, int MNC, int LAC, int CID) throws Exception{
		GeofenceBean result = null;
		result = processCellId(MCC, MNC, LAC, CID, false);
		return result;
	}
	
	public GeofenceBean processCellId(int MCC, int MNC, int LAC, int CID, boolean shortCID)
			throws Exception{

		GeofenceBean result = null;
		try {
			HttpURLConnection urlConnection = null;
			OutputStream out = null;
			DataInputStream dis = null;
			InputStream in = null;
			try {
				byte[] pd = PostData(MCC, MNC, LAC, CID, shortCID);
				
				final String baseURL = "http://www.google.com/glm/mmap";
				URL url = new URL(baseURL);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("POST");
				urlConnection.setRequestProperty("content-length", String.valueOf(pd.length));
				urlConnection.setDoOutput(true);
				
				out = urlConnection.getOutputStream();
				out.write(pd);

				if (logger != null) {
					logger.log(ILogger.LEVEL_INFO, "Trying connect to " + baseURL
							+ " to get lat/lon of " + "MCC:" + MCC + "|MNC:" + MNC
							+ "|LAC:" + LAC + "|CID:" + CID);
				}
				else {
					System.out.println("Trying connect to " + baseURL
							+ " to get lat/lon of " + "MCC:" + MCC + "|MNC:" + MNC
							+ "|LAC:" + LAC + "|CID:" + CID);
				}
				
				urlConnection.connect();
				in = urlConnection.getInputStream();
				dis = new DataInputStream(in);

				// Read some prior data
				dis.readShort();
				dis.readByte();
				// Read the error-code
				int errorCode = dis.readInt();
				if (errorCode == 0) {
					double lat = (double) dis.readInt() / 1000000D;
					double lng = (double) dis.readInt() / 1000000D;

					result = new GeofenceBean(lat, lng);

					// Read the rest of the data
					int accuracy = dis.readInt(); //accuracy from cell-tower (in Meter)
					result.setAccuracy(accuracy);
					
					dis.readInt();
					dis.readUTF();

					if (logger != null) {
						logger.log(ILogger.LEVEL_INFO, "Coordinate retrieved: "
								+ lat + "," + lng + "; from " + baseURL);
					}
					else {
						System.out.println("Coordinate retrieved: "
								+ lat + "," + lng + "; from " + baseURL);
					}
				}
				else {
					if (logger != null) {
						logger.log(ILogger.LEVEL_WARNING, "Cannot retrieve coordinate from " + baseURL
								+ " ERROR CODE: " + errorCode + " "
								+ "|MCC:" + MCC + "|MNC:" + MNC
								+ "|LAC:" + LAC + "|CID:" + CID);
					}
					else {
						System.out.println("Cannot retrieve coordinate from " + baseURL
								+ " ERROR CODE: " + errorCode + " "
								+ "|MCC:" + MCC + "|MNC:" + MNC
								+ "|LAC:" + LAC + "|CID:" + CID);
					}
				}
			}
			finally {
				if (dis != null)
					dis.close();
				if (in != null)
					in.close();
				if (out != null)
					out.close();

				urlConnection.disconnect();
			}
		}
		catch (Exception ex) {
			throw ex;
		}
		
		return result;
	}
	
	static byte[] PostData(int MCC, int MNC, int LAC, int CID, boolean shortCID) {
		/*
		 * The shortCID parameter follows heuristic experiences: Sometimes UMTS
		 * CIDs are build up from the original GSM CID (lower 4 hex digits) and
		 * the RNC-ID left shifted into the upper 4 digits.
		 */
		byte[] pd = new byte[] { 0x00, 0x0e, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1b,
				0x00, 0x00, 0x00, 0x00, // Offset 0x11
				0x00, 0x00, 0x00, 0x00, // Offset 0x15
				0x00, 0x00, 0x00, 0x00, // Offset 0x19
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // Offset 0x1f
				0x00, 0x00, 0x00, 0x00, // Offset 0x23
				0x00, 0x00, 0x00, 0x00, // Offset 0x27
				0x00, 0x00, 0x00, 0x00, // Offset 0x2b
				(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00, 0x00 };
		
		boolean isUMTSCell = ((long) CID > 65535);
		if (isUMTSCell) {
//			logger.log(ILogger.LEVEL_INFO, "UMTS CID. {0}" + (shortCID ? "Using short CID to resolve." : ""));
		}
		else {
//			logger.log(ILogger.LEVEL_INFO, "GSM CID given.");
		}
		
		if (shortCID)
			CID &= 0xFFFF;  /*
							 * Attempt to resolve the cell using the GSM CID
							 * part
							 */
		if ((long) CID > 65536) /* GSM: 4 hex digits, UTMS: 6 hex digits */
			pd[0x1c] = 5;
		else
			pd[0x1c] = 3;
		pd[0x11] = (byte) ((MNC >> 24) & 0xFF);
		pd[0x12] = (byte) ((MNC >> 16) & 0xFF);
		pd[0x13] = (byte) ((MNC >> 8) & 0xFF);
		pd[0x14] = (byte) ((MNC >> 0) & 0xFF);
		pd[0x15] = (byte) ((MCC >> 24) & 0xFF);
		pd[0x16] = (byte) ((MCC >> 16) & 0xFF);
		pd[0x17] = (byte) ((MCC >> 8) & 0xFF);
		pd[0x18] = (byte) ((MCC >> 0) & 0xFF);
		pd[0x27] = (byte) ((MNC >> 24) & 0xFF);
		pd[0x28] = (byte) ((MNC >> 16) & 0xFF);
		pd[0x29] = (byte) ((MNC >> 8) & 0xFF);
		pd[0x2a] = (byte) ((MNC >> 0) & 0xFF);
		pd[0x2b] = (byte) ((MCC >> 24) & 0xFF);
		pd[0x2c] = (byte) ((MCC >> 16) & 0xFF);
		pd[0x2d] = (byte) ((MCC >> 8) & 0xFF);
		pd[0x2e] = (byte) ((MCC >> 0) & 0xFF);
		pd[0x1f] = (byte) ((CID >> 24) & 0xFF);
		pd[0x20] = (byte) ((CID >> 16) & 0xFF);
		pd[0x21] = (byte) ((CID >> 8) & 0xFF);
		pd[0x22] = (byte) ((CID >> 0) & 0xFF);
		pd[0x23] = (byte) ((LAC >> 24) & 0xFF);
		pd[0x24] = (byte) ((LAC >> 16) & 0xFF);
		pd[0x25] = (byte) ((LAC >> 8) & 0xFF);
		pd[0x26] = (byte) ((LAC >> 0) & 0xFF);
		return pd;
	}
}
