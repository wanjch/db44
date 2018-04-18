package com.suptc.db44.mp.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Splitter;
import com.suptc.db44.mp.config.MpConfig;
import com.suptc.db44.mp.entity.Message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public class MessageUitls {
	private final static Logger log = LoggerFactory.getLogger(ByteBufUtil.class.getSimpleName());
	private final static String delimiter = MpConfig.get("delimiter");
	private final static String data_delimiter = MpConfig.get("data_delimiter");

	public static Message parse(String msgStr) {
		// "~T00&mp01&2&|"
		Message m = new Message();
		m.setOrigin(msgStr);
		msgStr = msgStr.substring(1);
		List<String> items = Splitter.on(delimiter).splitToList(msgStr);
		m.setFunction(items.get(0));
		m.setPlatform(items.get(1));
		String length = items.get(2);
		m.setLength(length);
		// 设置body
		if (Integer.parseInt(length) > 0) {
			m.setBody(Splitter.on(data_delimiter).splitToList(items.get(3)));
		}
		return m;
	}

	// 组装消息体
	private static String genMsgBody(String[] items) {
		if (items.length < 1) {
			throw new IllegalArgumentException("入参为空");
		}
		StringBuffer body = new StringBuffer();
		for (String item : items) {
			body.append(item);
			body.append(data_delimiter);
		}
		return body.substring(0, body.length() - 1);
	}

	// 组装消息全文
	public static String genMessage(String functionCode, String platformCode, String[] data) {
		StringBuffer msg = new StringBuffer();
		String body = genMsgBody(data);
		msg.append(MpConfig.get("begin")).append(functionCode).append(delimiter).append(platformCode).append(delimiter)
				.append(body.length()).append(delimiter).append(body).append(MpConfig.get("end"));
		return msg.toString();
	}

	public static String toString(Message message) {
		List<String> body = message.getBody();
		String[] data = null;
		if (body == null || body.size() < 1) {
			data = new String[] { "" };
		} else {
			data = body.toArray(new String[0]);
		}
		return genMessage(message.getFunction(), message.getPlatform(), data);
	}

	public static void send(ChannelHandlerContext ctx, Message message) {
		send(ctx, toString(message));
	}

	public static ChannelFuture send(ChannelHandlerContext ctx, String msg) {
		ByteBuf sendBuf = null;
		byte[] bytes = null;
		try {
			String charset = "gbk";// Config.get("charset")
			bytes = msg.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		sendBuf = Unpooled.buffer(bytes.length);
		sendBuf.writeBytes(bytes);
		return ctx.writeAndFlush(sendBuf);
	}

}
