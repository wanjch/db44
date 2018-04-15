package com.suptc.db44.imscp.handler;

import com.suptc.db44.handler.CommonChannelHandler;

import io.netty.channel.ChannelHandlerContext;

public class LastHandler extends CommonChannelHandler{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LoginHandler.decrease(ctx);
		log.info("移除channel：{}",ctx.channel().remoteAddress());
		log.info("当前online：{}",LoginHandler.onLine);
		super.exceptionCaught(ctx, cause);
	}

}
