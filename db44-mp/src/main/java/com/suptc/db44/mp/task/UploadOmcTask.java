package com.suptc.db44.mp.tasker;

import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.suptc.db44.config.Config;
import com.suptc.db44.entity.Message;
import com.suptc.db44.entity.Omc;
import com.suptc.db44.entity.Plate;

import io.netty.channel.ChannelHandlerContext;

public class UploadOmcTasker extends AbstractUploadTasker<Omc> {


	public UploadOmcTasker(ChannelHandlerContext ctx, String string) {
		super(ctx, string);
	}

	@Override
	Omc createEntity() {
		Plate p = new Plate();
		p.setPlateType("0");
		p.setCarPlate("粤S5s04sa");
		p.setCarPlateColor(3);
		
		Omc o=new Omc();
		o.setOmcId("omc123");
		o.setMdcNorm("mdcNorm111");
		o.setMdcId("mdcId001");
		o.setOrganizationCode("org233");
		o.setPlate(p);
		o.setVehicleIDCard("vehicleIDCard999");
		o.setVehicleOrganization("vehicleOrganization555");
		o.setVehicleTypeId("vehicleTypeId_666");
		o.setVehicleUseId("vehicleUseId_520");
		
		return o;
	}

	@Override
	Message createMsg() {
		Message m = new Message();
		m.setFunction("U06");
		m.setPlatform("mp_2");
//		m.setEnd(Config.get("end"));
		
		m.addData(createEntity().getOmcId());
		m.addData(createEntity().getMdcNorm());
		m.addData(createEntity().getMdcId());
		
		m.addData(createEntity().getPlate().getPlateType());
		m.addData(createEntity().getPlate().getCarPlate());
		m.addData(createEntity().getPlate().getCarPlateColor() + "");
		
		
		m.addData(createEntity().getVehicleTypeId());
		m.addData(createEntity().getVehicleUseId());
		m.addData(createEntity().getOrganizationCode());
		m.addData(createEntity().getVehicleOrganization());
		m.addData(createEntity().getVehicleIDCard());
		
		String length = String.valueOf(m.getBody().size() * 2 - 1);
		m.setLength(length);
		
		return m;
	}

}
