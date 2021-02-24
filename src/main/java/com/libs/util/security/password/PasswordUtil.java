package com.libs.util.security.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
	
	public static final String HASH_ALGORITHM = "SHA-256";
		
	public static String getPasswordHash(String password, String salt) throws NoSuchAlgorithmException {
		String hash = hash(salt + password);
		hash = hash(hash + salt);
		return hash;
	}
	
	private static String hash(String str) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
		md.update(str.getBytes());
		return String.format("%064x", new java.math.BigInteger(1, md.digest()));
		
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		new PasswordUtil();
		//		System.out.println("password : " + new PasswordUtil().getPasswordHash("joe", "saltpass"));
//		System.out.println("password : " + new PasswordUtil().getPasswordHash("asdasdasd", "saltpass"));
		System.out.println("password : " + PasswordUtil.getPasswordHash("12345", "12345"));
	}
}
