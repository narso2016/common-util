package com.libs.util.security.cryptograph;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.libs.util.encoder.Base64;

public class RC4 {
	protected static final String key20 = "8PHy8/T19vf4+YGCg4SFhoeIiZE=";
	protected static final byte[] byteKey = Base64.decode(key20);
	
	public static String decryptCryptoJs(String cipherText, String secret) throws Exception{
		byte[] cipherData = java.util.Base64.getDecoder().decode(cipherText);
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

		byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
		Cipher aesCBC = Cipher.getInstance("AES/ECB/PKCS5");
		aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decryptedData = aesCBC.doFinal(encrypted);
		String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

		System.out.println(decryptedText);
		return decryptedText;
	}
	
	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;

	    try {
	        md.reset();

	        // Repeat process until sufficient data has been generated
	        while (generatedLength < keyLength + ivLength) {

	            // Digest data (last digest if available, password data, salt if available)
	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);

	            // additional rounds
	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }

	            generatedLength += digestLength;
	        }

	        // Copy key and IV into separate byte arrays
	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	        return result;

	    } catch (DigestException e) {
	        throw new RuntimeException(e);

	    } finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}

	private static byte[] encrypt(byte[] toEncrypt, byte[] keys) throws Exception {
		SecretKeySpec key = new SecretKeySpec(keys, "RC4");
		// create an instance of cipher
		Cipher cipher = Cipher.getInstance("RC4");
		// initialize the cipher with the key
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// enctypt!
		byte[] encrypted = cipher.doFinal(toEncrypt);

		return encrypted;
	}

	private static byte[] decrypt(byte[] toDecrypt, byte[] keys) throws Exception {
		Cipher cipher = Cipher.getInstance("RC4");
		// create an instance of cipher
		SecretKeySpec key = new SecretKeySpec(keys, "RC4");
		// initialize the cipher with the key
		cipher.init(Cipher.DECRYPT_MODE, key);
		// decrypt!
		byte[] decrypted = cipher.doFinal(toDecrypt);
		return (decrypted);
	}

	public static String encrypt(byte[] toEncrypt) throws Exception {
		String result = "";
		result = new String(encrypt(toEncrypt, byteKey));
		return result;
	}

	public static String decrypt(byte[] toDecrypt) throws Exception {
		String result = "";
		result = new String(decrypt(toDecrypt, byteKey), "UTF-8");
		return result;
	}

	public static String encrypt(byte[] toEncrypt, String base64Key) throws Exception {
		String result = "";
		result = new String(encrypt(toEncrypt, base64Key.getBytes()));
		return result;
	}

	public static String decrypt(byte[] toDecrypt, String base64Key) throws Exception {
		String result = "";
		result = new String(decrypt(toDecrypt, base64Key.getBytes()), "UTF-8");
		return result;
	}

	public static void main(String[] args) {
		String test = "Vincentius Ardha Dian Rigitama";
		try {
			String encrypt = new String(SAK_Formater.encodebase64(RC4.encrypt(test.getBytes()).getBytes()));
			String decrypt = RC4.decrypt(SAK_Formater.decodebase64(encrypt));
			System.out.println(encrypt);
			System.out.println(decrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
