package com.suptc.db44.mp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {
	
	public static String encrypt(String plain) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("md5");
		byte[] cipherData = md5.digest(plain.getBytes());
		StringBuilder builder = new StringBuilder();
		for (byte cipher : cipherData) {
			String toHexStr = Integer.toHexString(cipher & 0xff);
			builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
		}
		return builder.toString();
	}

}
