package com.suptc.db44.imscp.login.checker;

import com.suptc.db44.config.Config;

public class RandomSerialCheck implements LoginCheck {
	String clientSerial;
	String localSerial;

	public RandomSerialCheck(String clientSerial, String localSerial) {
		super();
		this.clientSerial = clientSerial;
		this.localSerial = localSerial;
	}

	@Override
	public String doCheck() {
		return Config.get(clientSerial.equals(localSerial) ? "success" : "login_inject");
	}

}
