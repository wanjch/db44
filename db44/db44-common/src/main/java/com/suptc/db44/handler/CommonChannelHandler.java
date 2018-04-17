package com.suptc.db44.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.util.ChannelUtils;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 处理通用channel事件,如异常、channel关闭
 * 
 * @author wanjingchang
 *
 */
public class CommonChannelHandler extends ChannelDuplexHandler {

	protected Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ChannelUtils.shutdownTaskers(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error(ctx.channel() + " exceptionCaught", cause);
		ChannelUtils.closeChannelAndShutdownTaskers(ctx);
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
