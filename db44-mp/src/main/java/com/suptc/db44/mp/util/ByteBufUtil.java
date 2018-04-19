package com.suptc.db44.mp.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suptc.db44.mp.config.MpConfig;
import io.netty.buffer.ByteBuf;

public class ByteBufUtil {
	private final static Logger log = LoggerFactory.getLogger(ByteBufUtil.class.getSimpleName());

	public static String convertToString(ByteBuf msg, String charsetName) throws UnsupportedEncodingException {
		msg.markReaderIndex();
		byte[] dst = new byte[msg.readableBytes()];
		msg.readBytes(dst);
		msg.resetReaderIndex();
		log.debug("bytebuf string :{}", new String(dst, charsetName));
		return new String(dst, charsetName);
	}

	public static String convertToString(ByteBuf msg) throws UnsupportedEncodingException {
		String charset = MpConfig.get("charset");
		return convertToString(msg, charset);
	}

}
