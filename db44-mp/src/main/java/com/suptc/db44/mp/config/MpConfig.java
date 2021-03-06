package com.suptc.db44.mp.config;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MpConfig{
	private static String filename = "mp.properties";
	private static final Logger log = LoggerFactory.getLogger(MpConfig.class.getSimpleName());
	private static Properties p = new Properties();
	
	static {
		try {
			InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			p.load(fis);
		} catch (Exception e) {
			log.error("加载配置文件错误:" + filename, e);
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return p.getProperty(key.toLowerCase());
	}

	public static int getInt(String key) {
		return Integer.parseInt(p.getProperty(key.toLowerCase()));
	}

}
