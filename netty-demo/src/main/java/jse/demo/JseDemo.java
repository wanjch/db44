package jse.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JseDemo {
	Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Test
	public void test() {
		String s = "0001";
		log.info(s.getBytes().length + "");
	}

	@Test
	public void test2() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("b");
		list.add("b");
		list.add("b");
		String[] a = list.toArray(new String[0]);
		log.info(a.length + "");
	}

	@Test
	public void test3() {
		DateTime t1=DateTime.now();
		DateTime t2=DateTime.now();
		Duration d = new Duration(t1, t2);    
		long time = d.getStandardSeconds();    
		System.out.println(time);
	}

	@Test
	public void test4() {
		System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:dd:ss", Locale.CHINA));
		Map<String, Integer> loginCountRecord = new HashMap<>();
	}
	
	@Test
	public void test5() {
		Map<String, Integer> loginCountRecord = new HashMap<>();
		int c=loginCountRecord.get("a");
		log.info(c+"");
	}
}
