package com.suptc.db44.imscp.login.check;

import com.suptc.db44.imscp.config.ImscpConfig;

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
		return ImscpConfig.get(clientSerial.equals(localSerial) ? "success" : "login_inject");
	}

}
