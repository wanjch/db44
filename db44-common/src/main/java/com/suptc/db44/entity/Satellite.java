package com.suptc.db44.entity;

import java.util.Date;

/**
 * 卫星定位信息实体
 * 
 * @author wanjingchang
 *
 */
public class Satellite {

	Date SateliteTime;
	float Longitude;
	float Latitude;
	float Altitude;
	float SatelliteSpeed;
	float Speed;
	float Heading;
	Plate plate;
	String DriverIDCard;
	String VehicleIDCard;
	String CarState;

	public Date getSateliteTime() {
		return SateliteTime;
	}

	public void setSateliteTime(Date sateliteTime) {
		SateliteTime = sateliteTime;
	}

	public float getLongitude() {
		return Longitude;
	}

	public void setLongitude(float longitude) {
		Longitude = longitude;
	}

	public float getLatitude() {
		return Latitude;
	}

	public void setLatitude(float latitude) {
		Latitude = latitude;
	}

	public float getAltitude() {
		return Altitude;
	}

	public void setAltitude(float altitude) {
		Altitude = altitude;
	}

	public float getSatelliteSpeed() {
		return SatelliteSpeed;
	}

	public void setSatelliteSpeed(float satelliteSpeed) {
		SatelliteSpeed = satelliteSpeed;
	}

	public float getSpeed() {
		return Speed;
	}

	public void setSpeed(float speed) {
		Speed = speed;
	}

	public float getHeading() {
		return Heading;
	}

	public void setHeading(float heading) {
		Heading = heading;
	}

	public String getDriverIDCard() {
		return DriverIDCard;
	}

	public void setDriverIDCard(String driverIDCard) {
		DriverIDCard = driverIDCard;
	}

	public String getVehicleIDCard() {
		return VehicleIDCard;
	}

	public void setVehicleIDCard(String vehicleIDCard) {
		VehicleIDCard = vehicleIDCard;
	}

	public String getCarState() {
		return CarState;
	}

	public void setCarState(String carState) {
		CarState = carState;
	}

	public Plate getPlate() {
		return plate;
	}

	public void setPlate(Plate plate) {
		this.plate = plate;
	}

	@Override
	public String toString() {
		return "Satellite [SateliteTime=" + SateliteTime + ", Longitude=" + Longitude + ", Latitude=" + Latitude
				+ ", Altitude=" + Altitude + ", SatelliteSpeed=" + SatelliteSpeed + ", Speed=" + Speed + ", Heading="
				+ Heading + ", plate=" + plate + ", DriverIDCard=" + DriverIDCard + ", VehicleIDCard=" + VehicleIDCard
				+ ", CarState=" + CarState + "]";
	}

}
