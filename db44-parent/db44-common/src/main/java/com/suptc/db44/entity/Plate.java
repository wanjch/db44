package com.suptc.db44.entity;

/**
 * 车牌信息
 * 
 * @author lenovo
 *
 */
public class Plate {
	String PlateType;
	String CarPlate;
	int CarPlateColor;

	public String getPlateType() {
		return PlateType;
	}

	public void setPlateType(String plateType) {
		PlateType = plateType;
	}

	public String getCarPlate() {
		return CarPlate;
	}

	public void setCarPlate(String carPlate) {
		CarPlate = carPlate;
	}

	public int getCarPlateColor() {
		return CarPlateColor;
	}

	public void setCarPlateColor(int carPlateColor) {
		CarPlateColor = carPlateColor;
	}

	@Override
	public String toString() {
		return "Plate [PlateType=" + PlateType + ", CarPlate=" + CarPlate + ", CarPlateColor=" + CarPlateColor + "]";
	}
	
	
}
