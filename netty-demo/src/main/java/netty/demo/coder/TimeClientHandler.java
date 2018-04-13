package netty.demo.coder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 100; i++) {
			Date time = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String t ="~"+ format.format(time)+"#" ;
			ByteBuf buf = Unpooled.copiedBuffer(t.getBytes());
			ctx.writeAndFlush(buf).addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					System.out.println("send ok");
					
				}
			});
			
		}
	}

}