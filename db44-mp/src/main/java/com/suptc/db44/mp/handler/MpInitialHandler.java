package com.suptc.db44.mp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.handler.CommonChannelHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class MpInitialHandler extends ChannelInitializer<SocketChannel> {
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new LoggingHandler(LogLevel.INFO));
		p.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("#".getBytes("gbk"))));
		p.addLast("MpLoginHandler", new MpLoginHandler());
		p.addLast("MpTestLinkHandler", new MpTestLinkHandler());
		p.addLast("MpUploadHandler", new MpUploadHandler());
		p.addLast("CommonChannelHandler", new CommonChannelHandler());
		p.names().forEach(name -> log.info("channel {} 添加 handler: {}", new Object[] { ch, name }));
	}
}
