package com.libs.util.security.keygen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.Arrays;

public class SHA1KeyGenerator {
	SecureRandom sr = new SecureRandom();

	public SHA1KeyGenerator() {
		super();
	}

	public byte[] generateSHA1(byte[] ClearText) {

		SHA1Digest dSHA1 = new SHA1Digest();
		byte[] outSHA1 = new byte[dSHA1.getDigestSize()];
		dSHA1.update(ClearText, 0, ClearText.length);
		dSHA1.doFinal(outSHA1, 0);
		return outSHA1;

	}

	public ParametersWithIV GenerateKeyParameter() {
		byte[] iv128 = new byte[128 / 8];
		byte[] key128 = new byte[128 / 8];
		sr.nextBytes(key128);
		KeyParameter keyAES = new KeyParameter(key128);
		sr.nextBytes(iv128);
		ParametersWithIV paramsWithIV = new ParametersWithIV(keyAES, iv128);
		return paramsWithIV;

	}

	public byte[] customSha1() {
		String Messagestr = "admin";
		String postfix = "Caesar";
		byte[] Messagestrbyte = Messagestr.getBytes();
		byte[] postfixbyte = postfix.getBytes();
		SHA1Digest digest = new SHA1Digest();
		digest.update(Messagestrbyte, 0, Messagestrbyte.length);
		digest.update(postfixbyte, 0, postfixbyte.length);
		byte[] hash = new byte[digest.getDigestSize()];
		digest.doFinal(hash, 0);
		return hash;
	}

	public byte[] customSha1Calculate(String received) {
		int separation = received.indexOf('\n');
		String receivedHash = received.substring(separation + 1);
		String message = "";
		if (separation == -1) {
			message = received;
		} else {
			message = received.substring(0, separation);
		}

		byte[] messagebyte = message.getBytes();
		String postfix = "Caesar";
		byte[] postfixbyte = postfix.getBytes();
		SHA1Digest digest = new SHA1Digest();
		int plaintextLength = messagebyte.length - 20;
		byte[] plaintext = new byte[plaintextLength];
		byte[] hash = new byte[20];
		System.arraycopy(messagebyte, 0, plaintext, 0, plaintextLength);
		System.arraycopy(messagebyte, plaintextLength, hash, 0, 20);

		SHA1Digest digest2 = new SHA1Digest();
		digest2.update(plaintext, 0, plaintext.length);
		byte[] hash2 = new byte[digest2.getDigestSize()];
		digest2.doFinal(hash2, 0);
		if (Arrays.constantTimeAreEqual(hash, hash2)) {
			System.out.println("valid data");
		}

		digest.update(messagebyte, 0, messagebyte.length);
		digest.update(postfixbyte, 0, postfixbyte.length);
		byte[] calculatedhash = new byte[digest.getDigestSize()];
		digest.doFinal(calculatedhash, 0);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Base64Encoder base64 = new Base64Encoder();
		try {
			base64.decode(receivedHash, output);

		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] receivedHashbyte = output.toByteArray();
		System.out.println(new String(Hex.encode(receivedHashbyte)));
		System.out.println(new String(receivedHashbyte));

		if (Arrays.constantTimeAreEqual(receivedHashbyte, calculatedhash)) {
			System.out.println(new String(Hex.encode(receivedHashbyte)));
		}
		if (Arrays.constantTimeAreEqual(hash2, calculatedhash)) {
			System.out.println(new String(Hex.encode(receivedHashbyte)));
		}
		return receivedHashbyte;
	}

//	private byte[] calculateCMSKeyChecksum(byte[] key) {		
//		SHA1Digest dSHA1 = new SHA1Digest();
//		byte[] result = new byte[dSHA1.getDigestSize()];
//		
//		dSHA1.update(key, 0, key.length);
//		dSHA1.doFinal(result, 0);
//
//		System.arraycopy(result, 0, result, 0, 8);
//
//		return result;
//	}

//	private boolean checkCMSKeyChecksum(byte[] key, byte[] checksum) {
//		return Arrays.constantTimeAreEqual(calculateCMSKeyChecksum(key),checksum);
//	}
	
}