package com.suptc.db44.imscp.handler;

import com.suptc.db44.handler.CommonChannelHandler;
import com.suptc.db44.imscp.login.LoginInfo;

import io.netty.channel.ChannelHandlerContext;

public class LastHandler extends CommonChannelHandler{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LoginInfo.removeClient(ctx);
		log.info("移除channel：{}",ctx.channel().remoteAddress());
		log.info("当前online：{}",LoginInfo.onLine);
		super.exceptionCaught(ctx, cause);
	}

}
