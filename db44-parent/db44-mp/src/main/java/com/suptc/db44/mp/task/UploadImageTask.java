package com.suptc.db44.mp.task;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import com.suptc.db44.config.Config;
import com.suptc.db44.entity.Image;
import com.suptc.db44.entity.Message;
import com.suptc.db44.entity.Plate;
import com.suptc.db44.entity.Satellite;
import com.suptc.db44.mp.config.MpConfig;
import io.netty.channel.ChannelHandlerContext;

public class UploadImageTask extends AbstractUploadTask<Image>{

	public UploadImageTask(ChannelHandlerContext ctx, String zwName) {
		super(ctx, zwName);
	}

	@Override
	Image createEntity() {
		Image i=new Image();
		i.setSnapTime(DateTime.now().toDate());
		i.setImage(createImageDate());
		i.setSuffix("jpg");
		
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
		
		i.setSatellite(s);
		return i;
	}

	@Override
	Message createMsg() {
		Message m = new Message();
		m.setFunction("U07");
		m.setPlatform("mp_2");
//		m.setEnd(Config.get("end"));
		
		m.addData(DateFormatUtils.format(createEntity().getSnapTime(), Config.get("TIME_FORMAT"),
				Locale.CHINA));
		m.addData(new String(createEntity().getImage()));
		m.addData(createEntity().getSuffix());
		m.addData(createEntity().getSatellite().getAltitude() + "");
		m.addData(createEntity().getSatellite().getSatelliteSpeed() + "");
		m.addData(createEntity().getSatellite().getSpeed() + "");
		m.addData(createEntity().getSatellite().getHeading() + "");
		m.addData(createEntity().getSatellite().getPlate().getPlateType());
		m.addData(createEntity().getSatellite().getPlate().getCarPlate());
		m.addData(createEntity().getSatellite().getPlate().getCarPlateColor() + "");
		m.addData(createEntity().getSatellite().getVehicleIDCard());
		m.addData(createEntity().getSatellite().getVehicleIDCard());
		m.addData(createEntity().getSatellite().getCarState());
		
		String length = String.valueOf(m.getBody().size() * 2 - 1);
		m.setLength(length);
		
		return m;
	}

	private byte[] createImageDate(){
		String filepath=MpConfig.get("image_filepath");
		byte[] buf ;
		try {
			InputStream input=new FileInputStream(filepath);
			buf = new byte[input.available()];
			IOUtils.readFully(input, buf);
		} catch (IOException e) {
			this.log.error("读取图片文件异常", e);
			throw new RuntimeException(e);
		}
		buf="test image data".getBytes();
		return buf;
	}
	
	
}
