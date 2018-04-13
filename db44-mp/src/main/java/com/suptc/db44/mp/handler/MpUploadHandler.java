package com.suptc.db44.mp.handler;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suptc.db44.mp.config.MpConfig;
import com.suptc.db44.mp.tasker.UploadAlarmTasker;
import com.suptc.db44.mp.tasker.UploadSatelliteTasker;
import com.suptc.db44.mp.tasker.UploadSpeedingTasker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

public class MpUploadHandler extends ChannelInboundHandlerAdapter {
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	private UploadSatelliteTasker satelliteTask = null;
	private UploadSpeedingTasker speedingTask = null;
	private UploadAlarmTasker alarmTask = null;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		addUploadSatelliteTask(ctx);
		addUploadSpeedingTask(ctx);
		addUploadAlarmTask(ctx);
	}

	private void addUploadSatelliteTask(ChannelHandlerContext ctx) {
		log.info("添加 UploadSatelliteTask");
		if (satelliteTask == null) {
			satelliteTask = new UploadSatelliteTasker(ctx, "卫星定位");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(satelliteTask, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}
	}

	private void addUploadSpeedingTask(ChannelHandlerContext ctx) {
		log.info("添加 UploadSpeedingTask");
		if (speedingTask == null) {
			speedingTask = new UploadSpeedingTasker(ctx, "超速");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(speedingTask, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}
	}

	private void addUploadAlarmTask(ChannelHandlerContext ctx) {
		log.info("添加 UploadAlarmTask");
		if (alarmTask == null) {
			alarmTask = new UploadAlarmTasker(ctx, "报警");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(alarmTask, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}

	}

}
