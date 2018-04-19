package com.suptc.db44.imscp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class StringUtils {
	/**
	 * 生成随机字符串
	 * 
	 * @return
	 */
	public static String randomSerial() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}
	
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
