package com.suptc.db44.mp.tasker;

import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;

import com.suptc.db44.config.Config;
import com.suptc.db44.entity.Message;
import com.suptc.db44.entity.Plate;
import com.suptc.db44.entity.Satellite;

import io.netty.channel.ChannelHandlerContext;

/**
 * 卫星定位信息发送任务
 * 
 * @author lenovo
 *
 */
public class UploadSatelliteTasker extends AbstractUploadTasker<Satellite> {

	public UploadSatelliteTasker(ChannelHandlerContext ctx) {
		super(ctx);
	}

	public UploadSatelliteTasker(ChannelHandlerContext ctx, String zwName) {
		super(ctx, zwName);
	}

	@Override
	Satellite genEntity() {
		// 生成卫星定位实体
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
		p.setCarPlate("粤B5s04sa");
		p.setCarPlateColor(2);
		s.setPlate(p);
		return s;
	}

	@Override
	Message createMessage() {
		Message m = new Message();
		m.setFunction("U00");
		m.setPlatform("mp_2");
//		m.setEnd(Config.get("end"));

		m.addData(DateFormatUtils.format(genEntity().getSateliteTime(), Config.get("TIME_FORMAT"), Locale.CHINA));
		m.addData(genEntity().getLongitude() + "");
		m.addData(genEntity().getLatitude() + "");
		m.addData(genEntity().getAltitude() + "");
		m.addData(genEntity().getSatelliteSpeed() + "");
		m.addData(genEntity().getSpeed() + "");
		m.addData(genEntity().getHeading() + "");
		m.addData(genEntity().getPlate().getPlateType());
		m.addData(genEntity().getPlate().getCarPlate());
		m.addData(genEntity().getPlate().getCarPlateColor() + "");
		m.addData(genEntity().getVehicleIDCard());
		m.addData(genEntity().getVehicleIDCard());
		m.addData(genEntity().getCarState());

		String length = String.valueOf(m.getBody().size() * 2 - 1);
		m.setLength(length);
		
		return m;
	}

}
