package com.suptc.db44.imscp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.imscp.login.LoginInfo;
import com.suptc.db44.imscp.util.ChannelUtils;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class LastHandler extends ChannelDuplexHandler{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LoginInfo.removeClient(ctx);
		log.info("移除channel：{}",ctx.channel().remoteAddress());
		log.info("当前online：{}",LoginInfo.onLine);
		super.exceptionCaught(ctx, cause);
	}

	protected Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ChannelUtils.shutdownTaskers(ctx);
	}

	private void shutdownTasks(ChannelHandlerContext ctx) {
		EventLoop eventLoop = ctx.channel().eventLoop();
		eventLoop.shutdownGracefully().addListener(new GenericFutureListener() {
			@Override
			public void operationComplete(Future future) throws Exception {
				log.info("调用 EventLoop.shutdownGracefully(),关闭定时任务");
			}
		});
	}

}
