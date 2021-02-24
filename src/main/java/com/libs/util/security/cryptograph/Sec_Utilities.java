package com.libs.util.security.cryptograph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import com.libs.util.security.keygen.SHA1KeyGenerator;

public class Sec_Utilities {
	public static byte[] encodebase64(byte[] datatoencoded) {
		ByteArrayOutputStream outs = new ByteArrayOutputStream();

		try {
			Base64.encode(datatoencoded, 0, datatoencoded.length, outs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] base64bytes = outs.toByteArray();

		return base64bytes;
	}

	public static byte[] decodebase64(String data) {
//		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		byte[] databytes;
		try {
			databytes = data.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		databytes = Base64.decode(data);

		return databytes;
	}

	public static byte[] decodebase64(byte[] data) {
		byte[] databytes = new byte[] {};
		databytes = Base64.decode(data);
		return databytes;
	}

	public static byte[] decodeHex(byte[] hexdata) {
		byte[] outs = new byte[] {};
		outs = Hex.decode(hexdata);
		return outs;
	}

	public static byte[] decodeHex(String hexdata) {
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		try {
			Hex.decode(hexdata, outs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outs.toByteArray();
	}

	public static byte[] encodeHex(byte[] hexdata) {
		byte[] outs = Hex.encode(hexdata);
		return outs;
	}

	public static boolean IsValidSha1(byte[] Sha1HexSource, String SourceSha1) {

		boolean result = false;
		byte[] SourceByte = null;
		try {
			SourceByte = SourceSha1.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SHA1KeyGenerator shakeygen = new SHA1KeyGenerator();
		byte[] SourceSha1Byte = shakeygen.generateSHA1(SourceByte);
		byte[] SourceSha1HexByte = encodeHex(SourceSha1Byte);
		if (Sha1HexSource.length != SourceSha1HexByte.length)
			return false;
		if (new String(Sha1HexSource).equals(new String(SourceSha1HexByte))) {
			result = true;
		}

		return result;
	}

}