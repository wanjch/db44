package com.suptc.db44.mp.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.mp.config.MpConfig;
import com.suptc.db44.mp.task.UploadAlarmTask;
import com.suptc.db44.mp.task.UploadCrossBorderTask;
import com.suptc.db44.mp.task.UploadFatigueDrivingTask;
import com.suptc.db44.mp.task.UploadImageTask;
import com.suptc.db44.mp.task.UploadOmcTask;
import com.suptc.db44.mp.task.UploadSatelliteTask;
import com.suptc.db44.mp.task.UploadSpeedingTask;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

public class MpUploadHandler extends ChannelInboundHandlerAdapter {
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	private UploadSatelliteTask satelliteTask = null;
	private UploadSpeedingTask speedingTask = null;
	private UploadAlarmTask alarmTask = null;
	private UploadFatigueDrivingTask fatigueDrivingTasker = null;
	private UploadCrossBorderTask crossBorderTasker = null;
	private UploadOmcTask omcTasker = null;
	private UploadImageTask imageTasker = null;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		addUploadSatelliteTask(ctx);
		addUploadSpeedingTask(ctx);
		addUploadAlarmTask(ctx);
		addUploadFatigueDrivingTasker(ctx);
		addUploadCrossBorderTasker(ctx);
		addUploadOmcTasker(ctx);
		addUploadImageTasker(ctx);
	}

	private void addUploadImageTasker(ChannelHandlerContext ctx) {
		log.info("添加 UploadImageTasker");
		if (this.imageTasker == null) {
			imageTasker = new UploadImageTask(ctx, "图片");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(imageTasker, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}

	}

	private void addUploadOmcTasker(ChannelHandlerContext ctx) {
		log.info("添加 UploadOmcTasker");
		if (this.omcTasker == null) {
			omcTasker = new UploadOmcTask(ctx, "OMC及车辆静态");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(omcTasker, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}
	}

	/**
	 * @param ctx
	 */
	private void addUploadCrossBorderTasker(ChannelHandlerContext ctx) {
		log.info("添加 UploadCrossBorderTasker");
		if (this.crossBorderTasker == null) {
			crossBorderTasker = new UploadCrossBorderTask(ctx, "越界行驶");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(crossBorderTasker, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}
	}

	/**
	 * @param ctx
	 */
	private void addUploadFatigueDrivingTasker(ChannelHandlerContext ctx) {
		log.info("添加 UploadFatigueDrivingTasker");
		if (this.fatigueDrivingTasker == null) {
			fatigueDrivingTasker = new UploadFatigueDrivingTask(ctx, "疲劳驾驶");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(fatigueDrivingTasker, 0, MpConfig.getInt("upload_interval"),
					TimeUnit.SECONDS);
		}
	}

	private void addUploadSatelliteTask(ChannelHandlerContext ctx) {
		log.info("添加 UploadSatelliteTask");
		if (satelliteTask == null) {
			satelliteTask = new UploadSatelliteTask(ctx, "卫星定位");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(satelliteTask, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}
	}

	private void addUploadSpeedingTask(ChannelHandlerContext ctx) {
		log.info("添加 UploadSpeedingTask");
		if (speedingTask == null) {
			speedingTask = new UploadSpeedingTask(ctx, "超速");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(speedingTask, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}
	}

	private void addUploadAlarmTask(ChannelHandlerContext ctx) {
		log.info("添加 UploadAlarmTask");
		if (alarmTask == null) {
			alarmTask = new UploadAlarmTask(ctx, "报警");
			// 每3秒钟检查一次链路
			EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.scheduleAtFixedRate(alarmTask, 0, MpConfig.getInt("upload_interval"), TimeUnit.SECONDS);
		}

	}

}
