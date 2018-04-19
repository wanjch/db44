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
 * 处理客户端链路检测
 * 
 * @author wanjingchang
 *
 */
public class ImscpTestLinkHandler extends ChannelInboundHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	int count = 0;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读取消息，并转为Message对象：
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		if (m.getFunction().equals(ImscpConfig.get("test_link_req"))) {
			log.info("channel {} received TEST_LINK:{}", ctx.channel(), m);
			checkLinkRsp(ctx, m);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	/*
	 * 链路检测响应
	 */
	private void checkLinkRsp(ChannelHandlerContext ctx, Message m) {
		/*
		 * // 模拟一段时间后停止链路检测响应 if (++count > 6) { return; }
		 */
		m.setFunction(ImscpConfig.get("TEST_LINK_RSP"));
		log.info("channel {} send TEST_LINK_RSP:{}", ctx.channel(), m);
		MessageUitls.send(ctx, m);
	}

}
