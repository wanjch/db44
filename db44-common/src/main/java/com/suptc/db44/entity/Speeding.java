package com.suptc.db44.entity;

/**
 * 卫星定位信息实体
 * 
 * @author wanjingchang
 *
 */
public class Speeding {

	Satellite satellite;

	public Satellite getSatellite() {
		return satellite;
	}

	public void setSatellite(Satellite s) {
		this.satellite = s;
	}

	@Override
	public String toString() {
		return "Speeding [s=" + satellite + "]";
	}
	

}
