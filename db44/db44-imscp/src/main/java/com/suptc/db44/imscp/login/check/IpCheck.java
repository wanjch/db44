package com.suptc.db44.imscp.login.check;

import com.suptc.db44.imscp.config.ImscpConfig;

public class IpCheck implements LoginCheck {

	private String ip;

	public IpCheck(String ip) {
		this.ip = ip;
	}

	@Override
	public String doCheck() {
		String validIps = ImscpConfig.get("valid_ip");
		return ImscpConfig.get(validIps.contains(this.ip) ? "success" : "req_inject");
	}

}
