package netty.demo.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	int count = 0;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] dst = new byte[buf.readableBytes()];
		buf.readBytes(dst);
		String req = new String(dst);
		System.out.println("count = " + (++count) + " , req = " + req);
	}

}