package com.suptc.db44.imscp.util;

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

/**
 * Channel工具类
 * 
 * @author wanjingchang
 *
 */
public class ChannelUtils {

	/**
	 * 日志对象
	 */
	final static Logger log = LoggerFactory.getLogger(ChannelUtils.class.getSimpleName());

	/**
	 * 获取对方ip
	 * 
	 * @param channel
	 * @return ip
	 */
	public static String remoteIp(Channel channel) {
		InetSocketAddress addr = (InetSocketAddress) channel.remoteAddress();
		return addr.getHostName();
	}

	/**
	 * 关闭channel
	 * 
	 * @param channel
	 */
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

	/**
	 * 关闭channel
	 * 
	 * @param ChannelHandlerContext
	 */
	public static void closeChannel(ChannelHandlerContext ctx) {
		closeChannel(ctx.channel());
	}

	/**
	 * 停止channel绑定的定时任务
	 * 
	 * @param channel
	 */
	public static void shutdownTaskers(Channel channel) {
		EventLoop eventLoop = channel.eventLoop();
		if (eventLoop.isShutdown() || eventLoop.isTerminated()) {
			return;
		}
		eventLoop.forEach(e -> log.debug("{}", e));
		eventLoop.shutdownGracefully().addListener(new GenericFutureListener() {
			@Override
			public void operationComplete(Future future) throws Exception {
				log.info("channel {} 绑定的 Taskers 已解除", channel);
			}
		});
	}

	/**
	 * 停止channel绑定的定时任务
	 * 
	 * @param ChannelHandlerContext
	 */
	public static void shutdownTaskers(ChannelHandlerContext ctx) {
		shutdownTaskers(ctx.channel());
	}

	/**
	 * 关闭channel并停止绑定的定时任务
	 * 
	 * @param 
	 */
	public static void closeChannelAndShutdownTaskers(ChannelHandlerContext ctx) {
		closeChannelAndShutdownTaskers(ctx.channel());
	}

	/**
	 * 关闭channel并停止绑定的定时任务
	 * 
	 * @param channel
	 */
	public static void closeChannelAndShutdownTaskers(Channel channel) {
		shutdownTaskers(channel);
		closeChannel(channel);
	}
	
}
