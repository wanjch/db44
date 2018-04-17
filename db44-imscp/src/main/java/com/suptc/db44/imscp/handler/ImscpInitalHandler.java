package com.suptc.db44.imscp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.handler.CommonChannelHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class ImscpInitalHandler extends ChannelInitializer<SocketChannel>{
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("#".getBytes("gbk"))));
		//先判断是否登录
		p.addLast("LoginHandler", new LoginHandler());
		p.addLast("CheckClientAciveHandler", new CheckClientAciveHandler());
		p.addLast("ImscpTestLinkHandler", new ImscpTestLinkHandler());
		p.addLast("UploadHandler", new UploadHandler());
		p.addLast("LastHandler", new LastHandler());
		p.names().forEach(name -> log.info("添加 handler: {}",name));
		
	}

}
