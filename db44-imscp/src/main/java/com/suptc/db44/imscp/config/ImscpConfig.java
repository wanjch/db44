package com.suptc.db44.imscp.config;


import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suptc.db44.config.Config;

public final class ImscpConfig extends Config {
	private static String filename = "imscp.properties";
	private static final Logger log = LoggerFactory.getLogger(Config.class.getSimpleName());
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
	
	public static Properties getProp(){
		return p;
	}

}
