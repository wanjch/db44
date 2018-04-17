package com.suptc.db44.mp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suptc.db44.entity.Message;
import com.suptc.db44.mp.config.MpConfig;
import com.suptc.db44.config.Config;
import com.suptc.db44.util.ByteBufUtil;
import com.suptc.db44.util.ChannelUtils;
import com.suptc.db44.util.MessageUitls;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MpLoginHandler extends ChannelInboundHandlerAdapter {
	private final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 读取消息，并转为Message对象：
		Message m = MessageUitls.parse(ByteBufUtil.convertToString((ByteBuf) msg));
		log.debug("received origin:{}", m.getOrigin());

		// 服务端发送随机序列
		if (m.getFunction().equals(Config.get("send_randomstr"))) {
			// 发送登录申请
			loginReq(ctx, m);
			// 禁止next handler处理
			return;
		}
		// 服务端发送登录回应
		if (m.getFunction().equals(Config.get("login_rsp"))) {
			String loginResult = handleLoginRsp(ctx, m);
			// 登录失败，则不做下一步操作
			if (!loginResult.equals(Config.get("success"))) {
				return;
			}
		}
		//
		super.channelRead(ctx, msg);
	}

	// 登录申请
	private void loginReq(ChannelHandlerContext ctx, Message m) {
		// 读取并保存市平台发送的随机字符串
		String randomStr = m.getBody().get(0);
		// 模拟数据
		String username = MpConfig.get("username");
		String password = MpConfig.get("password");
		String platform = "mp01";
		String functionCode = "L01";

		// 组装消息
		String loginReq = MessageUitls.genMessage(functionCode, platform,
				new String[] { randomStr, username, password });
		log.info("send login req:{}", loginReq);
		// 发送
		MessageUitls.send(ctx, loginReq);
	}

	/**
	 * 处理服务端发送的登录响应,若登录失败关闭channel
	 * 
	 * @param ctx
	 * @param m
	 * @return
	 */
	private String handleLoginRsp(ChannelHandlerContext ctx, Message m) {
		String result = m.getBody().get(0);
		// 登录成功，交由next handler处理
		if (result.equals(Config.get("success"))) {
			log.info("登录成功--->{}", m);
		} else {// 登录失败，关闭channel
			log.info("登录失败:{}", m);
			ChannelUtils.closeChannel(ctx);
		}
		return result;
	}

}
