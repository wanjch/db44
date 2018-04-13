package jse.demo;

import netty.demo.stringcoder.StringClient;

public class StaticDemo {
	static StringClient sc=new StringClient(22, "aaa");
	public static void main(String[] args) {
		StaticDemo d=new StaticDemo();
		StaticDemo d1=new StaticDemo();
		StaticDemo d2=new StaticDemo();
		System.out.println(d.sc);
		System.out.println(d1.sc);
		System.out.println(d2.sc);
	}
}
