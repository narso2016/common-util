package com.libs.util.security.cryptograph;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.libs.util.encoder.Base64;

public class AESencrp {

	  private static final String ALGO = "AES";
	  private static final byte[] keyValue = 
	      new byte[] { '4', '1', '2', 'v', 'a', 'd', 'd',
	      'h', 'a', 'r', '4','f', '4', 'e', 'l', '!'};

	  public static String encrypt(String data) throws Exception {
	    Key key = generateKey();
	    Cipher c = Cipher.getInstance(ALGO);
	    c.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encVal = c.doFinal(data.getBytes());
	    String encryptedValue = new String(Base64.encode(encVal));
	    return encryptedValue;
	  }


	  private static Key generateKey() throws Exception {
	    Key key = new SecretKeySpec(keyValue, ALGO);
	    return key;
	  }
	  
	  public static String decrypt(String data) throws Exception{
		  	byte[] bts = Base64.decode(data);
		    Key key = generateKey();
		    Cipher c = Cipher.getInstance(ALGO);
		    c.init(Cipher.DECRYPT_MODE, key);
		    byte[] encVal = c.doFinal(bts);
		    String encryptedValue = new String(encVal);
		    return encryptedValue;
	  }
	  
	  public static void main(String[] args) throws Exception {
//		  System.out.println(Base64.encode(keyValue));
		  String text = "12345";
		  String encri = AESencrp.encrypt(text);
		  System.out.println(encri);
		  String decri = AESencrp.decrypt(encri);
		  System.out.println(decri);
	  }
	}