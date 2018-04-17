package com.suptc.db44.imscp.login.checker;

import com.suptc.db44.config.Config;
import com.suptc.db44.imscp.config.ImscpConfig;

public class PasswordCheck implements LoginCheck {

	String password;
	String username;

	public PasswordCheck(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String doCheck() {
		return Config.get(isValidPassword(username, password) ? "success" : "password_invalid");
	}

	private boolean isValidPassword(String username, String password) {
		return ImscpConfig.get(username).equals(password);
	}

}
