package com.libs.util.security.cryptograph;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class SHA256 {
	
	public static String hashData(String plainText,String plainTextSaltBytes) throws IOException, NoSuchAlgorithmException {
		
		byte[] buf = null;
		
		buf = StringToHex(plainTextSaltBytes);
	    
		byte[] key = new byte[8];
	        
        for(int i = 0; i < 8; i++){
        	key[i]= buf[buf.length-8+i];
        }
        
        byte[] plainTextBytes = plainText.getBytes("UTF-8");

        // Allocate array, which will hold plain text and salt.
        byte[] plainTextWithSaltBytes =
                new byte[plainTextBytes.length + key.length];

        // Copy plain text bytes into resulting array.
        for (int i = 0; i < plainTextBytes.length; i++)
            plainTextWithSaltBytes[i] = plainTextBytes[i];

        // Append salt bytes to the resulting array.
        for (int i = 0; i < key.length; i++)
            plainTextWithSaltBytes[plainTextBytes.length + i] = key[i];
		
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // Compute hash value of our plain text with appended salt.
        byte[] hashBytes = md.digest(plainTextWithSaltBytes);
        // Create array which will hold hash and original salt bytes.
        byte[] hashWithSaltBytes = new byte[hashBytes.length +
                                            key.length];

        // Copy hash bytes into resulting array.
        for (int i = 0; i < hashBytes.length; i++)
            hashWithSaltBytes[i] = hashBytes[i];

        // Append salt bytes to the result.
        for (int i = 0; i < key.length; i++)
            hashWithSaltBytes[hashBytes.length + i] = key[i];

        // Convert result into a base64-encoded string.
        //String hashValue = Convert.ToBase64String(hashWithSaltBytes);
        
        String hashValue = StringFromHex(hashWithSaltBytes);

		return hashValue;
	}
	
	private static String StringFromHex(byte[] b){
		String encryptedData= Base64.getEncoder().encodeToString(b);
		return encryptedData;
	}
	
	private static byte[] StringToHex(String text) throws IOException{
		byte[] base64DecodedData = Base64.getDecoder().decode(text);
		return base64DecodedData;
	}
}
