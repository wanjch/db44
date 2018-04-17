package com.suptc.db44.imscp.login.checker;

import java.util.ArrayList;
import java.util.List;

import com.suptc.db44.config.Config;

public class LoginCheckChain implements LoginCheck {

	List<LoginCheck> Checks = new ArrayList<LoginCheck>();

	// 用于标记规则的引用顺序
	int index = 0;

	// 往规则链条中添加规则
	public LoginCheckChain addLoginCheck(LoginCheck f) {
		Checks.add(f);
		return this;
	}

	@Override
	public String doCheck() {
		String success = Config.get("success");
		for (LoginCheck check : Checks) {
			String result = check.doCheck();
			if (!result.equals(success)) {
				return result;
			}
		}
		return success;
	}

}
