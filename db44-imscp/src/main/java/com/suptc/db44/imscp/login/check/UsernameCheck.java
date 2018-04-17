package com.suptc.db44.imscp.login.check;

import com.suptc.db44.config.Config;
import com.suptc.db44.imscp.config.ImscpConfig;

public class UsernameCheck implements LoginCheck {
	String username;
	
	public UsernameCheck(String username) {
		this.username = username;
	}

	private boolean isValidUsername(String username) {
		return ImscpConfig.getProp().containsKey(username);
	}

	@Override
	public String doCheck() {
		return Config.get(isValidUsername(username)?"success":"username_invalid");
	}
}
