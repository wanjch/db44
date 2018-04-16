package com.suptc.db44.mp.tasker;

import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;

import com.suptc.db44.config.Config;
import com.suptc.db44.entity.CrossBorder;
import com.suptc.db44.entity.Message;
import com.suptc.db44.entity.Plate;
import com.suptc.db44.entity.Satellite;

import io.netty.channel.ChannelHandlerContext;

/**
 * 报警信息发送任务
 * 
 * @author wanjingchang
 *
 */
public class UploadCrossBorderTasker extends AbstractUploadTasker<CrossBorder> {

	public UploadCrossBorderTasker(ChannelHandlerContext ctx) {
		super(ctx);
	}

	public UploadCrossBorderTasker(ChannelHandlerContext ctx, String zwName) {
		super(ctx, zwName);
	}

	@Override
	public Message createMsg() {
		Message m = new Message();
		m.setFunction("U04");
		m.setPlatform("mp_2");
//		m.setEnd(Config.get("end"));
		
		m.addData( genEntity().getCrossBorderType()+"");
		m.addData(DateFormatUtils.format(genEntity().getSatellite().getSateliteTime(), Config.get("TIME_FORMAT"),
				Locale.CHINA));
		m.addData(genEntity().getSatellite().getLongitude() + "");
		m.addData(genEntity().getSatellite().getLatitude() + "");
		m.addData(genEntity().getSatellite().getAltitude() + "");
		m.addData(genEntity().getSatellite().getSatelliteSpeed() + "");
		m.addData(genEntity().getSatellite().getSpeed() + "");
		m.addData(genEntity().getSatellite().getHeading() + "");
		m.addData(genEntity().getSatellite().getPlate().getPlateType());
		m.addData(genEntity().getSatellite().getPlate().getCarPlate());
		m.addData(genEntity().getSatellite().getPlate().getCarPlateColor() + "");
		m.addData(genEntity().getSatellite().getVehicleIDCard());
		m.addData(genEntity().getSatellite().getVehicleIDCard());
		m.addData(genEntity().getSatellite().getCarState());
		
		String length = String.valueOf(m.getBody().size() * 2 - 1);
		m.setLength(length);
		
		return m;
	}

	@Override
	CrossBorder genEntity() {
		// 生成报警实体
		Satellite s = new Satellite();
		s.setSateliteTime(DateTime.now().toDate());
		s.setLongitude(11.23f);
		s.setLatitude(202.56f);
		s.setAltitude(5269);
		s.setSatelliteSpeed(34.59f);
		s.setSpeed(40.33f);
		s.setHeading(29.32f);

		s.setDriverIDCard("jsz48999");
		s.setVehicleIDCard("vehicle0022");
		s.setCarState("carstate_666");

		Plate p = new Plate();
		p.setPlateType("1");
		p.setCarPlate("粤B11aab0");
		p.setCarPlateColor(2);
		s.setPlate(p);

		CrossBorder c = new CrossBorder();
		c.setSatellite(s);
		c.setCrossBorderType(new Random().nextInt(100));;
		return c;
	}

}
