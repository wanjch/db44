package com.suptc.db44.mp.task;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.mp.config.MpConfig;
import com.suptc.db44.util.ChannelUtils;
import com.suptc.db44.util.MessageUitls;

import io.netty.channel.ChannelHandlerContext;

/**
 * 定时发送链路检测任务
 * 
 * @author wanjingchang
 *
 */
public class MpTestLinkTask implements Runnable {
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	ChannelHandlerContext ctx;
	// 链路最近有效时间
	private DateTime lastActiveTime;

	public void refreshLastActiveTime() {
		this.lastActiveTime = DateTime.now();
	}

	public MpTestLinkTask(ChannelHandlerContext ctx) {
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
		log.debug("lastActiveTime:{}", lastActiveTime);
		long internal = new Duration(lastActiveTime, DateTime.now()).getStandardSeconds();
		int threshold = MpConfig.getInt("test_link_retry") * MpConfig.getInt("test_link_interval");
		// 链路失效，关闭
		if (internal >= threshold) {
			isActive = false;
			log.info("channel {} disconnected for {} seconds ", this.ctx.channel(), internal);
			ChannelUtils.closeChannel(this.ctx);
		}
		return isActive;
	}

}
