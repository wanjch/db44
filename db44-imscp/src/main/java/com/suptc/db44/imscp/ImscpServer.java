package com.suptc.db44.imscp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.config.Config;
import com.suptc.db44.imscp.handler.ImscpInitalHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ImscpServer {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	private int port;

	public ImscpServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ImscpInitalHandler())
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture f = b.bind(port);
			log.info(" binding at {}", port);
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					log.info("bind {} ,channel = {}", future.isSuccess() ? "ok" : "failed", future.channel());
				}
			});
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new ImscpServer(Config.getInt("port")).run();
	}

}
