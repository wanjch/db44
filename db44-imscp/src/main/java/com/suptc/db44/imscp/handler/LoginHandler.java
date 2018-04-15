package com.suptc.db44.imscp.handler;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suptc.db44.config.Config;
import com.suptc.db44.entity.Message;
import com.suptc.db44.imscp.checker.IpCheck;
import com.suptc.db44.imscp.checker.LoginCheckChain;
import com.suptc.db44.imscp.checker.PasswordCheck;
import com.suptc.db44.imscp.checker.RandomSerialCheck;
import com.suptc.db44.imscp.checker.UsernameCheck;
import com.suptc.db44.util.ByteBufUtil;
import com.suptc.db44.util.ChannelUtils;
import com.suptc.db44.util.MessageUitls;
import com.suptc.db44.util.StringUtils;

import io.netty.buffer.ByteBuf;
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

	// 同一ip登录 统计 （key为ip),多客户端共享
	public static Map<String, Integer> loginCountRecord = new Hashtable<>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 判定登录是否超限
		String remoteIp = ChannelUtils.remoteIp(ctx.channel());
		Integer count = loginCountRecord.get(remoteIp);
		if (count != null && count >= Config.getInt("client_online_limit")) {
			log.info("channel:{} 因登录数 超限，拒绝登录", ctx.channel());
			ChannelUtils.closeChannel(ctx);
		} else {
			// 发送随机字符串,并存档
			sendAndSaveRandomSerial(ctx);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读取消息，并转为Message对象：
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		log.debug("received ByteBuf:{}", m.getOrigin());

		if (m.getFunction().equals(Config.get("LOGIN_REQ"))) {
			log.info("received LOGIN_REQ-:{}", m);
			handleLoginReq(ctx, m);
		} else {
			super.channelRead(ctx, msg);
		}
	}

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

		String remoteIp = ChannelUtils.remoteIp(ctx.channel());
		if (result.equals(Config.get("SUCCESS"))) {// 登录成功
			// 更新该ip的登录数
			loginCountRecord.put(remoteIp,
					loginCountRecord.containsKey(remoteIp) ? loginCountRecord.get(remoteIp) + 1 : 1);
			log.info("client {} 登录成功，当前该ip登录数: {}(注：null表示 0)", ctx.channel(), loginCountRecord.get(remoteIp));
		} else {// 登录失败
			log.info(" channel {} login failed", ctx.channel());
			future.addListener(ChannelFutureListener.CLOSE);
		}

		// 删除随机字符串
		randomSerialRecord.remove(ctx.channel().remoteAddress());
	}

	private String check(ChannelHandlerContext ctx, Message m) {
		LoginCheckChain chain = new LoginCheckChain();
		IpCheck ipCheck = new IpCheck(ChannelUtils.remoteIp(ctx.channel()));
		RandomSerialCheck SerialCheck = new RandomSerialCheck(randomSerialRecord.get(ctx.channel().remoteAddress()),
				m.getBody().get(0));
		UsernameCheck UsernameCheck = new UsernameCheck(m.getBody().get(1));
		PasswordCheck PasswordCheck = new PasswordCheck(m.getBody().get(1), m.getBody().get(2));

		chain.addLoginCheck(ipCheck).addLoginCheck(SerialCheck).addLoginCheck(UsernameCheck)
				.addLoginCheck(PasswordCheck);

		String result = chain.doCheck();
		return result;
	}
//~L01&mp01&27&f1c63144cb#|suptc_3|suptc_1#
}
