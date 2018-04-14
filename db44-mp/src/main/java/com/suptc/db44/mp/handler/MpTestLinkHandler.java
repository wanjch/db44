package com.suptc.db44.mp.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.config.Config;
import com.suptc.db44.entity.Message;
import com.suptc.db44.mp.tasker.MpTestLinkTasker;
import com.suptc.db44.util.ByteBufUtil;
import com.suptc.db44.util.MessageUitls;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

public class MpTestLinkHandler extends ChannelInboundHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	MpTestLinkTasker task = null;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		initCheckLinkTask(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		log.info("received origin {}",m);
		// 市平台发送链路检测响应
		if (m.getFunction().equals(Config.get("TEST_LINK_RSP"))) {
			log.info("received TEST_LINK_RSP {}",m);
			task.refreshLastActiveTime();
		} else {// 其它功能，交由下一个handler处理msg
			ctx.fireChannelRead(msg);
		}
	}

	public void initCheckLinkTask(ChannelHandlerContext ctx) {
		if (task == null) {
			log.info("添加 链路检测 任务");
			task = new MpTestLinkTasker(ctx);
			task.refreshLastActiveTime();
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(task, 0, 3, TimeUnit.SECONDS);
		}
	}

}