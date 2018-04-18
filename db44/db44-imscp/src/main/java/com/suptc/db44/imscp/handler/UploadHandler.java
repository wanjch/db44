package com.suptc.db44.imscp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.imscp.config.ImscpConfig;
import com.suptc.db44.imscp.entity.Message;
import com.suptc.db44.imscp.util.ByteBufUtil;
import com.suptc.db44.imscp.util.MessageUitls;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理监控平台上传数据
 * 
 * @author wanjingchang
 *
 */
public class UploadHandler extends ChannelInboundHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读取消息，并转为Message对象：
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		if (m.getFunction().contains(ImscpConfig.get("UPLOAD"))) {
			handleUpload(ctx, m);
		} else {
			ctx.fireChannelRead(msg);
		}

	}

	/**处理监控平台上传数据
	 * @param ctx
	 * @param m
	 */
	private void handleUpload(ChannelHandlerContext ctx, Message m) {
		log.info("channel {} received UPLOAD {}", ctx.channel(), m.getOrigin());
	}

}
