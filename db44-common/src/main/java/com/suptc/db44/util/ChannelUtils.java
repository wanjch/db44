package com.suptc.db44.util;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ChannelUtils {

	final static Logger log = LoggerFactory.getLogger(ChannelUtils.class.getSimpleName());

	public static String remoteIp(Channel channel) {
		InetSocketAddress addr = (InetSocketAddress) channel.remoteAddress();
		return addr.getHostName();
	}

	public static void closeChannel(Channel channel) {
		if (!channel.isActive()) {
			return;
		}
		channel.close().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				log.info("channel {} 已关闭", channel);
			}
		});
	}

	public static void closeChannel(ChannelHandlerContext ctx) {
		closeChannel(ctx.channel());
	}

	public static void shutdownTasks(Channel channel) {
		EventLoop eventLoop = channel.eventLoop();
		if(eventLoop.isShutdown() ||eventLoop.isTerminated()){
			return ;
		}
		eventLoop.forEach(e->log.debug("{}", e));
		eventLoop.shutdownGracefully().addListener(new GenericFutureListener() {
			@Override
			public void operationComplete(Future future) throws Exception {
				log.info("channel {} 绑定的 Tasks 已解除", channel);
			}
		});
	}

	public static void shutdownTasks(ChannelHandlerContext ctx) {
		shutdownTasks(ctx.channel());
	}
	
	public static void closeChannelAndShutdownTasks(ChannelHandlerContext ctx){
		closeChannelAndShutdownTasks(ctx.channel());
	}

	public static void closeChannelAndShutdownTasks(Channel channel) {
		shutdownTasks(channel);
		closeChannel(channel);
	}
}
