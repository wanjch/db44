package com.suptc.db44.mp.handler;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suptc.db44.mp.config.MpConfig;
import com.suptc.db44.mp.entity.Message;
import com.suptc.db44.mp.task.MpTestLinkTask;
import com.suptc.db44.mp.util.ByteBufUtil;
import com.suptc.db44.mp.util.MessageUitls;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

/**
 * 链路检测器，用于检测服务端是否保持有效连接
 * 
 * @author wanjingchang
 *
 */
public class MpTestLinkHandler extends ChannelInboundHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	MpTestLinkTask task = null;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		initCheckLinkTask(ctx);
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		log.debug("received origin {}", m.getOrigin());
		// 市平台发送链路检测响应
		if (m.getFunction().equals(MpConfig.get("TEST_LINK_RSP"))) {
			log.info("received TEST_LINK_RSP {}", m);
			task.refreshLastActiveTime();
		} else {// 其它功能，交由下一个handler处理msg
			ctx.fireChannelRead(msg);
		}
	}

	public void initCheckLinkTask(ChannelHandlerContext ctx) {
		if (task == null) {
			log.info("添加 链路检测 任务");
			task = new MpTestLinkTask(ctx);
			task.refreshLastActiveTime();
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(task, 0, MpConfig.getInt("test_link_interval"), TimeUnit.SECONDS);
		}
	}

}