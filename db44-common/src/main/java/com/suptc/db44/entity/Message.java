package com.suptc.db44.entity;

import java.util.ArrayList;
import java.util.List;

import com.suptc.db44.config.Config;

public class Message {
	
	String origin;
	
	String begin = Config.get("begin");
	String function;
	String platform;
	String length;
	List<String> body;
//	String end = Config.get("end");

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

//	public String getEnd() {
//		return end;
//	}
//
//	public void setEnd(String end) {
//		this.end = end;
//	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public List<String> getBody() {
		return body;
	}

	public void setBody(List<String> body) {
		this.body = body;
	}

	public void addData(String data) {
		if (this.body == null) {
			this.body = new ArrayList<>();
		}
		this.body.add(data);
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String msgStr) {
		this.origin = msgStr;
	}

	@Override
	public String toString() {
		return "Message [begin=" + begin + ", function=" + function + ", platform=" + platform
				+ ", length=" + length + ", body=" + body + "]";
	}


}
