package com.suptc.db44.mp.entity;

/**
 * 报警信息
 * 
 * @author wanjingchang
 *
 */
public class Alarm {

	String alarmType;
	Satellite satellite;
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public Satellite getSatellite() {
		return satellite;
	}
	public void setSatellite(Satellite satellite) {
		this.satellite = satellite;
	}
	@Override
	public String toString() {
		return "Alarm [alarmType=" + alarmType + ", satellite=" + satellite + "]";
	}
	
	
}
