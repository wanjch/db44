package com.suptc.db44.util;

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
}
