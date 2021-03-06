package com.suptc.db44.mp.task;

import com.suptc.db44.mp.entity.Message;
import com.suptc.db44.mp.entity.Omc;
import com.suptc.db44.mp.entity.Plate;

import io.netty.channel.ChannelHandlerContext;

public class UploadOmcTask extends AbstractUploadTask<Omc> {


	public UploadOmcTask(ChannelHandlerContext ctx, String string) {
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
