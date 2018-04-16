package com.suptc.db44.mp.tasker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.entity.Message;
import com.suptc.db44.util.MessageUitls;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
		String upload=createMsgStr();
		MessageUitls.send(ctx, upload).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture arg0) throws Exception {
				log.info("channel {} 已上传 {} 信息--->{}",new Object[]{ctx.channel(),zwName, upload});
			}
		});
	}

	abstract T createEntity();

	abstract Message createMsg() ;
	
	String createMsgStr() {
		Message m = createMsg();
		return MessageUitls.toString(m);
	}
	
}
