package com.suptc.db44.imscp.task;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.imscp.config.ImscpConfig;
import com.suptc.db44.imscp.login.LoginInfo;
import com.suptc.db44.util.ChannelUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

// 检测2 分钟内未发送 登陆申请或递交链路检测请求
public class CheckClientActiveTask implements Runnable {
	final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	private ChannelHandlerContext ctx;
	// 客户端是否发送消息
	private DateTime lastClientReqTime;

	public CheckClientActiveTask(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		long internal = new Duration(getLastClientReqTime(), DateTime.now()).getStandardSeconds();
		if (internal > ImscpConfig.getInt("client_req_internal")) {
			final Channel channel = ctx.channel();
			log.info("channel {},最近 {} s内未收到 客户端 登陆申请或递交链路检测请求", channel, internal);
			handleLogout(ctx);
			ChannelUtils.closeChannelAndShutdownTaskers(ctx);
		}
	}

	private void handleLogout(ChannelHandlerContext ctx) {
		// 更新该ip登录数
		String remoteIp = ChannelUtils.remoteIp(ctx.channel());

		Integer count = LoginInfo.onLine.get(remoteIp);
		if (count == null) {
			return;
		}
		if (count <= 1) {
			LoginInfo.onLine.remove(remoteIp);
		} else {
			LoginInfo.onLine.put(remoteIp, --count);
		}
	}

	public void refreshClientReqTime() {
		this.setLastClientReqTime(DateTime.now());
	}

	public DateTime getLastClientReqTime() {
		return lastClientReqTime;
	}

	public void setLastClientReqTime(DateTime lastClientReqTime) {
		this.lastClientReqTime = lastClientReqTime;
	}

}
