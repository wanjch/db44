package com.suptc.db44.mp.tasker;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.util.ChannelUtils;
import com.suptc.db44.util.MessageUitls;

import io.netty.channel.ChannelHandlerContext;

/**
 * 定时发送链路检测任务
 * 
 * @author wanjingchang
 *
 */
public class MpCheckLinkTasker implements Runnable {
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	ChannelHandlerContext ctx;
	// 链路最近有效时间
	private DateTime lastActiveTime;

	public void refreshLastActiveTime() {
		this.lastActiveTime = DateTime.now();
	}

	public MpCheckLinkTasker(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		if (isActive()) {
			sendLinkReq();
		}
	}

	/**
	 * 发送链路测试数据
	 */
	private void sendLinkReq() {
		String functionCode = "T00";
		String platform = "mp01";
		// 组装消息
		String message = MessageUitls.genMessage(functionCode, platform, new String[] { "" });
		log.info("channel send test link:{}", this.ctx.channel(), message);
		MessageUitls.send(ctx, message);
	}

	/**
	 * 检测链路是否存活
	 */
	private boolean isActive() {
		boolean isActive = true;
		log.info("lastActiveTime:{}", lastActiveTime);
		DateTime now = DateTime.now();
		long internal = new Duration(lastActiveTime, now).getStandardSeconds();
		// 链路失效，关闭
		if (internal >= 20) {
			isActive = false;
			log.info("channel {} disconnected for {} seconds ", this.ctx.channel(), internal);
			ChannelUtils.closeChannel(this.ctx);
		}
		return isActive;
	}

}
