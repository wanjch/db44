package com.suptc.db44.imscp.handler;

import com.suptc.db44.handler.CommonChannelHandler;

import io.netty.channel.ChannelHandlerContext;

public class LastHandler extends CommonChannelHandler{

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		this.log.info("移除该客户端");
		LoginHandler.decrease(ctx);
		super.exceptionCaught(ctx, cause);
	}

}
