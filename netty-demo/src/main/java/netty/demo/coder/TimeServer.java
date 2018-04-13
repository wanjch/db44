package netty.demo.coder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class TimeServer {
	private int port;

	public TimeServer(int port) {
		this.port = port;
	}

	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		ServerBootstrap server = new ServerBootstrap().group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("#".getBytes())));
						pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("~".getBytes())));
						// ×Ô¼ºµÄÂß¼­Handler
						pipeline.addLast("handler", new TimeServerHandler());
					}
				});

		try {
			ChannelFuture future = server.bind(port).sync();
			System.out.println(this);
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		TimeServer server = new TimeServer(9001);
		server.start();
	}
}
