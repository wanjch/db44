package com.suptc.db44.imscp.login;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import com.suptc.db44.imscp.config.ImscpConfig;
import com.suptc.db44.util.ChannelUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 登录统计信息
 * 
 * @author wanjingchang
 *
 */
public class LoginInfo {

	// 同一ip登录 统计 （key为ip),多客户端共享
	public static Map<String, Integer> onLine = new Hashtable<>();
	// 保存已登录的客户端列表
	public static Set<SocketAddress> clients = new HashSet<>();

	public static boolean isLogin(ChannelHandlerContext ctx) {
		SocketAddress remoteAddress = ctx.channel().remoteAddress();
		return clients.contains(remoteAddress);
	}

	public static boolean isLogin(Channel ch) {
		SocketAddress remoteAddress = ch.remoteAddress();
		return clients.contains(remoteAddress);
	}

	/**
	 * 判断在线数是否超限
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isOnLineOver(ChannelHandlerContext ctx) {
		// 判定登录是否超限
		String remoteIp = ChannelUtils.remoteIp(ctx.channel());
		Integer count = onLine.get(remoteIp);
		return count != null && count >= ImscpConfig.getInt("client_online_limit");
	}

	public static void increase(ChannelHandlerContext ctx) {
		String remoteIp = ChannelUtils.remoteIp(ctx.channel());
		// 更新该ip的登录数
		onLine.put(remoteIp, onLine.containsKey(remoteIp) ? onLine.get(remoteIp) + 1 : 1);
	}

	public static void decrease(ChannelHandlerContext ctx) {
		String remoteIp = ChannelUtils.remoteIp(ctx.channel());
		if (!onLine.containsKey(remoteIp)) {
			return;
		}
		Integer online = onLine.get(remoteIp);
		if (online > 1) {
			onLine.put(remoteIp, online - 1);
		} else {
			onLine.remove(remoteIp);
		}
	}

	public static void addClient(ChannelHandlerContext ctx) {
		clients.add(ctx.channel().remoteAddress());
		increase(ctx);
	}

	public static void removeClient(ChannelHandlerContext ctx) {
		clients.remove(ctx.channel().remoteAddress());
		decrease(ctx);
	}

}
