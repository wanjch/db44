package com.suptc.db44.imscp.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.imscp.tasker.CheckClientActiveTask;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

/**
 * 检测客户端连接是否有效
 * @author wanjingchang
 *
 */
public class CheckClientAciveHandler extends ChannelInboundHandlerAdapter{
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	private CheckClientActiveTask task = null;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		addClientActiveTaskTask(ctx);
		super.channelActive(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 客户端已发送请求,更新该状态值
		task.refreshClientReqTime();
		super.channelRead(ctx, msg);
	}

	private void addClientActiveTaskTask(ChannelHandlerContext ctx) {
		if (task == null) {
			task = new CheckClientActiveTask(ctx);
			task.refreshClientReqTime();
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(task, 0, 3, TimeUnit.SECONDS);
			log.info("添加 任务：检测客户端是否在规定时间内 登陆申请或递交链路检测请求");
		}
	}
}
