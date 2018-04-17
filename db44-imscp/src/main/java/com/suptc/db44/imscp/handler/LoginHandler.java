package com.suptc.db44.imscp.handler;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.config.Config;
import com.suptc.db44.entity.Message;
import com.suptc.db44.imscp.login.LoginInfo;
import com.suptc.db44.imscp.login.checker.IpCheck;
import com.suptc.db44.imscp.login.checker.LoginCheckChain;
import com.suptc.db44.imscp.login.checker.PasswordCheck;
import com.suptc.db44.imscp.login.checker.RandomSerialCheck;
import com.suptc.db44.imscp.login.checker.UsernameCheck;
import com.suptc.db44.util.ByteBufUtil;
import com.suptc.db44.util.ChannelUtils;
import com.suptc.db44.util.MessageUitls;
import com.suptc.db44.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * (安全认证)登录申请处理
 * 
 * @author wanjingchang
 *
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	// 随机序列 记录
	// TODO （key为ip?ip+port)
	Map<SocketAddress, String> randomSerialRecord = new HashMap<>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 判定登录是否超限
		if (LoginInfo.isOnLineOver(ctx)) {
			log.info("channel:{} 因登录数 超限，拒绝登录", ctx.channel());
			ChannelUtils.closeChannel(ctx);
		} else {
			// 发送随机字符串,并存档
			sendAndSaveRandomSerial(ctx);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 若已登录，则交由next handler处理
		if (LoginInfo.isLogin(ctx)) {
			super.channelRead(ctx, msg);
			return;
		}
		
		// 若未登录，则处理登录申请
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		log.debug("received ByteBuf:{}", m.getOrigin());
		if (m.getFunction().equals(Config.get("LOGIN_REQ"))) {
			log.info("received LOGIN_REQ : {}", m);
			handleLoginReq(ctx, m);
		} 
	}

	/**
	 * 发送随机字符串，并本地存档
	 * 
	 * @param ctx
	 */
	private void sendAndSaveRandomSerial(ChannelHandlerContext ctx) {
		String serial = StringUtils.randomSerial();
		String functionCode = "L00";
		String plantformCode = "mp01";
		String msg = MessageUitls.genMessage(functionCode, plantformCode, new String[] { serial });
		log.info("发送 随机字符串 -:{}", msg);
		MessageUitls.send(ctx, msg);
		// 保存发送该ip的随机序列
		randomSerialRecord.put(ctx.channel().remoteAddress(), serial);
	}

	/**
	 * 处理登录申请
	 * 
	 * @param ctx
	 * @param m
	 */
	private void handleLoginReq(ChannelHandlerContext ctx, Message m) {
		String result = check(ctx, m);

		// 发送登录响应
		String functionCode = "L02";
		String platformCode = "mp01";
		String msg = MessageUitls.genMessage(functionCode, platformCode, new String[] { result });
		log.info("send login_rsp:{}", msg);
		ChannelFuture future = MessageUitls.send(ctx, msg);

		if (result.equals(Config.get("SUCCESS"))) {// 登录成功
			// 更新该ip的登录数
			LoginInfo.addClient(ctx);
			log.info("client {} 登录成功，当前online: {}", ctx.channel(), LoginInfo.onLine);
		} else {// 登录失败
			log.info(" channel {} login failed", ctx.channel());
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * 登录校验
	 * 
	 * @param ctx
	 * @param m
	 * @return 结果码
	 */
	private String check(ChannelHandlerContext ctx, Message m) {
		Channel channel = ctx.channel();
		LoginCheckChain chain = new LoginCheckChain();
		String remoteIp = ChannelUtils.remoteIp(channel);
		log.info("remoteIp :{}",remoteIp);
		IpCheck ipCheck = new IpCheck(remoteIp);
		RandomSerialCheck SerialCheck = new RandomSerialCheck(randomSerialRecord.get(channel.remoteAddress()),
				m.getBody().get(0));
		UsernameCheck UsernameCheck = new UsernameCheck(m.getBody().get(1));
		PasswordCheck PasswordCheck = new PasswordCheck(m.getBody().get(1), m.getBody().get(2));

		chain.addLoginCheck(ipCheck).addLoginCheck(SerialCheck).addLoginCheck(UsernameCheck)
				.addLoginCheck(PasswordCheck);

		return chain.doCheck();
	}

}
