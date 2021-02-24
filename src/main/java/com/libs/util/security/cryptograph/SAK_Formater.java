package com.libs.util.security.cryptograph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import com.libs.util.security.keygen.AESKeyGenerator;
import com.libs.util.security.keygen.SHA1KeyGenerator;

public class SAK_Formater {

	public static String DecipherData(String ReceivedData) {
		String result = "";
		AESKeyGenerator AESKeygen = new AESKeyGenerator();

		if (ReceivedData.length() > 0) {
			// Decoding Base64 BouncyCrypto menjadi Hex Value
			byte[] istr = decodebase64(ReceivedData);

			// Parser byte format dalam Hex Value
			// SHA1Hex + CipherDataHex + AESKeyHex

			byte[] SHA1ByteHex = new byte[40];
			byte[] AESKeyByteHex = new byte[64];
			int datasize = istr.length - (40 + 64);
			byte[] cipherdataByteHex = new byte[datasize];
			if (istr.length > 40) {
				System.arraycopy(istr, 0, SHA1ByteHex, 0, SHA1ByteHex.length);
				System.arraycopy(istr, datasize + 40, AESKeyByteHex, 0, AESKeyByteHex.length);
				System.arraycopy(istr, 40, cipherdataByteHex, 0, cipherdataByteHex.length);
			}

			// Decoding hex dari semua HexValue
			byte[] AESKeyOri = Hex.decode(AESKeyByteHex);
			byte[] cipherdata = Hex.decode(cipherdataByteHex);

			// process Decrypt AES256
			AES_Manager Aes_Mgr = new AES_Manager();
			KeyParameter AesKey256 = AESKeygen.generateNewAESKey(AESKeyOri);

			try {
				byte[] dec = Aes_Mgr.DecryptAES256(cipherdata, AesKey256);
				result = new String(dec);
				// System.out.println("hasil:" + result);
			} catch (InvalidCipherTextException e) {
				e.printStackTrace();
			}

			boolean IsSha1 = IsValidSha1(SHA1ByteHex, result);
			if (IsSha1) {
				return result;
			} else {
				return "Err: This Data been Hijacked !!!";
			}

		}
		return result;

	}

	public static byte[] CipherData(String SendingData) {
		byte[] result = null;
		SHA1KeyGenerator shakeygen = new SHA1KeyGenerator();
		byte[] sha1keybyte = null;
		byte[] sha1key = null;
		AESKeyGenerator aeskeygen = new AESKeyGenerator();
		byte[] seeds = aeskeygen.generateNewSeeds();
		KeyParameter aesKey256 = aeskeygen.generateNewAESKey(seeds);
		AES_Manager aesmg = new AES_Manager();
		try {
			byte[] cipher = aesmg.encryptAES256(SendingData.getBytes(), aesKey256);
			byte[] hexcipherbyte = Hex.encode(cipher);
			byte[] decryptcipher = aesmg.DecryptAES256(cipher, aesKey256);
			String ciphertext = new String(decryptcipher);
			sha1keybyte = ciphertext.getBytes();
			sha1key = shakeygen.generateSHA1(sha1keybyte);

			String datatosend = new String(Hex.encode(sha1key)) + new String(hexcipherbyte)
					+ new String(Hex.encode(seeds));

			byte[] senddataenc = decodebase64(datatosend);
			byte[] backdecode = encodebase64(senddataenc);
			result = encodebase64(backdecode);

		} catch (InvalidCipherTextException e) {
			System.out.println(e.getMessage());

		}
		return result;
	}

	public static byte[] encodebase64(byte[] datatoencoded) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			Base64.encode(datatoencoded, 0, datatoencoded.length, out);
		} catch (IOException e) {

		}
		byte[] base64bytes = out.toByteArray();

		return base64bytes;
	}

	public static byte[] decodebase64(String data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			Base64.decode(data, out);
		} catch (IOException e) {

		}
		byte[] base64bytes = out.toByteArray();

		return base64bytes;
	}

	public static boolean IsValidSha1(byte[] Sha1HexSource, String SourceSha1) {
		boolean result = false;

		byte[] SourceByte = SourceSha1.getBytes();
		SHA1KeyGenerator shakeygen = new SHA1KeyGenerator();
		byte[] SourceSha1Byte = shakeygen.generateSHA1(SourceByte);
		byte[] SourceSha1HexByte = Hex.encode(SourceSha1Byte);
		if (Sha1HexSource.length != SourceSha1HexByte.length)
			return false;
		if (new String(Sha1HexSource).equals(new String(SourceSha1HexByte))) {
			result = true;
		}

		return result;
	}

	public static void main(String[] args) {
		byte[] a = SAK_Formater.CipherData("Vincentius Ardha Dian Rigitama");
		System.out.println("String a = " + new String(a));
		String b = SAK_Formater.DecipherData(new String(a));
		System.out.println("String b = " + b);
	}

}
