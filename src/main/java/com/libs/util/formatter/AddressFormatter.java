package com.libs.util.formatter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.libs.util.google.GoogleAddressRender;
import com.libs.util.google.GoogleResponseResult;
import com.libs.util.google.GoogleResult;
import com.libs.util.log.ILogger;
import com.libs.util.strings.Strings;

public class AddressFormatter {
	private static final String separator = ",";
	// private static final String[] STRING_LOOP =
	// {"addressStreet1","addressStreet2","noaddr","kelurahan","kecamatan","kabupaten","rt","rw","city","kodepos","propinsi","negara"};
//	private static final String[] STRING_CODE = 
//		{ "A1=", "A2=", "NO=", "KL=", "KC=", "KB=", 
//		   "RT=", "RW=", "CT=", "PC=", "PR=", "NG=" };

	public static String formatAddress(String address) {
		String[] addresses = address.split(AddressFormatter.separator);
		String result = "";
		for (int i = 0; i < addresses.length; i++) {
			String temp = addresses[i].replaceAll(" ", "+");

			temp = (temp.startsWith("+")) ? temp.substring(1) : temp;
			temp = (temp.endsWith("+")) ? temp.substring(0, temp.length() - 1) : temp;
			result = (i < addresses.length - 1) ? result + temp + ",+" : result + temp;

		}
		return result;
	}

	public static String generateAddress(String addressStreet1, String addressStreet2, String noaddr, String kelurahan,
			String kecamatan, String kabupaten, String rt, String rw, String city, String kodepos, String propinsi,
			String negara) {
		String result = Strings.isNullOrEmpty(addressStreet1) ? "" : addressStreet1;
		String address2 = Strings.isNullOrEmpty(addressStreet2) ? "" : addressStreet2;
		String no = Strings.isNullOrEmpty(noaddr) ? "" : noaddr;
		String kel = Strings.isNullOrEmpty(kelurahan) ? "" : kelurahan;
		String kec = Strings.isNullOrEmpty(kecamatan) ? "" : kecamatan;
		String kab = Strings.isNullOrEmpty(kabupaten) ? "" : kabupaten;
		String addrRt = Strings.isNullOrEmpty(rt) ? "" : rt;
		String addrRw = Strings.isNullOrEmpty(rw) ? "" : rw;
		String rtrw = "RT/RW:" + addrRt + "/" + addrRw;
		String kota = Strings.isNullOrEmpty(city) ? "" : city;
		String kdPos = Strings.isNullOrEmpty(kodepos) ? "" : kodepos;
		String prop = Strings.isNullOrEmpty(propinsi) ? "" : propinsi;
		String neg = Strings.isNullOrEmpty(negara) ? "" : negara;

		result = (!Strings.isNullOrEmpty(address2)) ? result + " " + address2 : result;
		result = (!Strings.isNullOrEmpty(no)) ? result + " " + no : result;
		result = (!Strings.isNullOrEmpty(addrRt) && !Strings.isNullOrEmpty(addrRw)) ? result + ", " + rtrw : result;
		result = (!Strings.isNullOrEmpty(kel)) ? result + ", Kel:" + kel : result;
		result = (!Strings.isNullOrEmpty(kec)) ? result + ", Kec:" + kec : result;
		result = (!Strings.isNullOrEmpty(kab)) ? result + ", Kab:" + kab : result;
		result = (!Strings.isNullOrEmpty(kota)) ? result + ", Kota " + kota : result;
		result = (!Strings.isNullOrEmpty(prop)) ? result + ", " + prop : result;
		result = (!Strings.isNullOrEmpty(neg)) ? result + ", " + neg : result;
		result = (!Strings.isNullOrEmpty(kdPos)) ? result + ", " + kdPos : result;

		if (Strings.isNullOrEmpty(result))
			return null;
		return result;
	}

	public static String generateLoopAddress(String addressStreet1, String addressStreet2, String noaddr,
			String kelurahan, String kecamatan, String kabupaten, String rt, String rw, String city, String kodepos,
			String propinsi, String negara) {
		String result = "";
		String[] addresses = new String[11];

		addresses[0] = Strings.isNullOrEmpty(addressStreet1) ? "" : addressStreet1;
		addresses[1] = Strings.isNullOrEmpty(addressStreet2) ? "" : addressStreet2;
		addresses[2] = Strings.isNullOrEmpty(noaddr) ? "" : noaddr;
		addresses[3] = Strings.isNullOrEmpty(kelurahan) ? "" : kelurahan;
		addresses[4] = Strings.isNullOrEmpty(kecamatan) ? "" : kecamatan;
		addresses[5] = Strings.isNullOrEmpty(kabupaten) ? "" : kabupaten;
		String addrRt = Strings.isNullOrEmpty(rt) ? "" : rt;
		String addrRw = Strings.isNullOrEmpty(rw) ? "" : rw;
		addresses[6] = "RT/RW:" + addrRt + "/" + addrRw;
		addresses[7] = Strings.isNullOrEmpty(city) ? "" : city;
		addresses[8] = Strings.isNullOrEmpty(propinsi) ? "" : propinsi;
		addresses[9] = Strings.isNullOrEmpty(negara) ? "" : negara;
		addresses[10] = Strings.isNullOrEmpty(kodepos) ? "" : kodepos;

		for (int i = 0; i < addresses.length; i++) {
			if (!Strings.isNullOrEmpty(addresses[i])) {
				if (i >= 0 && i < 2)
					result = result + addresses[i] + " ";
				else if (i == addresses.length - 1)
					result = result + addresses[i];
				else
					result = result + addresses[i] + ", ";
			} else {
				continue;
			}
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map googleConvert(ILogger logger, String addressStreet1, String addressStreet2, String noaddr,
			String kecamatan, String kabupaten, String city, String kodepos, String propinsi, String negara) {

		String latitude = "0";
		String longitude = "0";
		Map resultMap = new HashMap();
		try {
			String result = "";
			String[] addresses = new String[6];

			String addr1 = Strings.isNullOrEmpty(addressStreet1) ? "" : addressStreet1;
			String addr2 = Strings.isNullOrEmpty(addressStreet2) ? "" : addressStreet2;
			String streetAddr = addr1 + (Strings.isNullOrEmpty(addr2) ? "" : " " + addr2);
			String noAddr = Strings.isNullOrEmpty(noaddr) ? "" : noaddr;
			String baseAddress = streetAddr + (Strings.isNullOrEmpty(noAddr) ? "" : " " + noAddr);
			String kodePos = Strings.isNullOrEmpty(kodepos) ? "" : " " + kodepos;

			addresses[0] = baseAddress;
			addresses[1] = Strings.isNullOrEmpty(kecamatan) ? "" : kecamatan;
			addresses[2] = Strings.isNullOrEmpty(kabupaten) ? "" : kabupaten;
			addresses[3] = Strings.isNullOrEmpty(city) ? "" : city;
			addresses[4] = Strings.isNullOrEmpty(propinsi) ? ("" + kodePos) : (propinsi + kodePos);
			addresses[5] = Strings.isNullOrEmpty(negara) ? "" : negara;

			for (int i = 0; i < addresses.length; i++) {
				if (!Strings.isNullOrEmpty(addresses[i])) {
					result = result + addresses[i] + ",";
				} else {
					continue;
				}
			}

			if (!Strings.isNullOrEmpty(result) && result.length() > 1) {
				result = result.substring(0, result.length() - 1);
				GoogleResponseResult response = GoogleAddressRender
						.convertToLatLong(AddressFormatter.formatAddress(result));
				if (response.getStatus().equals("OK")) {
					for (GoogleResult gResult : response.getResults()) {
						latitude = String.valueOf(gResult.getGeometry().getLocation().getLat());
						longitude = String.valueOf(gResult.getGeometry().getLocation().getLng());
					}
				}
			}
		} catch (IOException e) {
			logger.printStackTrace(e);
			e.printStackTrace();
		}

		resultMap.put("lat", latitude);
		resultMap.put("lng", longitude);
		return resultMap;

	}

}
