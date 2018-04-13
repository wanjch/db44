package com.suptc.db44.imscp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.config.Config;
import com.suptc.db44.entity.Message;
import com.suptc.db44.util.ByteBufUtil;
import com.suptc.db44.util.MessageUitls;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class UploadHandler extends ChannelInboundHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读取消息，并转为Message对象：
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		if (m.getFunction().contains(Config.get("UPLOAD"))) {
			handleUpload(ctx, m);
		} else {
			ctx.fireChannelRead(msg);
		}

	}

	private void handleUpload(ChannelHandlerContext ctx, Message m) {
		log.info("channel {} received UPLOAD {}", ctx.channel(), m.getOrigin());
	}

}
