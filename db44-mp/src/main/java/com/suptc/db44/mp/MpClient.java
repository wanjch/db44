package com.suptc.db44.mp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.config.Config;
import com.suptc.db44.mp.handler.MpInitialHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MpClient {
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	public static void main(String[] args) throws Exception {
		// 发起连接
		new MpClient().connect(Config.get("server_ip"), Config.getInt("port"));
	}

	public void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.handler(new MpInitialHandler());
			
			ChannelFuture f = b.connect(host, port);
			log.info(" connecting {}:{}", host, port);
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					log.info("{} ,channel = {}",future.isSuccess()?"connected":"disconnected",future.channel());
				}
			});
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			group.shutdownGracefully();
		}
	}

}