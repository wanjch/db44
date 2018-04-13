package netty.demo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloWordServerHandler extends ChannelInboundHandlerAdapter {
	private int counter = 0;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("counter=" + ++counter);
		ByteBuf buf = (ByteBuf) msg;
		byte[] dst = new byte[buf.readableBytes()];
		buf.readBytes(dst);
		String req = new String(dst);
		System.out.println("request:" + req);
	}

}