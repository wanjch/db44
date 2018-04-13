package com.suptc.db44.mp.tasker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.entity.Message;
import com.suptc.db44.util.MessageUitls;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractUploadTasker<T> implements Runnable{
	final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
	ChannelHandlerContext ctx;
	String zwName;

	public AbstractUploadTasker(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	public AbstractUploadTasker(ChannelHandlerContext ctx, String zwName) {
		super();
		this.ctx = ctx;
		this.zwName = zwName;
	}

	@Override
	public void run() {
		Message m = createMessage();
		log.info("channel {} 上传 {} 信息--->{}",new Object[]{this.ctx.channel(),this.zwName, m});
		MessageUitls.send(ctx, m);
	}

	abstract T genEntity();

	abstract Message createMessage() ;
	
}
