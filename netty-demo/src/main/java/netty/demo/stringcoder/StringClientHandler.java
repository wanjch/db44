package netty.demo.stringcoder;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class StringClientHandler extends ChannelInboundHandlerAdapter {
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	 System.out.println("channelActive");
    	 String body="channelActive";
    	 ChannelFuture f = ctx.writeAndFlush(body);
    	 f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client is close");
    }
    
    
}