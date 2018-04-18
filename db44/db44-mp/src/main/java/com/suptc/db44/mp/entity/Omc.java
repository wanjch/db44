package com.suptc.db44.mp.entity;
/**
 * OMC及车辆静态信息
 * @author wanjingchang
 *
 */
public class Omc {

	String omcId;
	String mdcNorm;
	String mdcId;
	Plate plate;
	String vehicleTypeId;
	String vehicleUseId;
	String OrganizationCode;
	String vehicleOrganization;
	String vehicleIDCard;
	
	public String getOmcId() {
		return omcId;
	}
	public void setOmcId(String omcId) {
		this.omcId = omcId;
	}
	public String getMdcNorm() {
		return mdcNorm;
	}
	public void setMdcNorm(String mdcNorm) {
		this.mdcNorm = mdcNorm;
	}
	public String getMdcId() {
		return mdcId;
	}
	public void setMdcId(String mdcId) {
		this.mdcId = mdcId;
	}
	public Plate getPlate() {
		return plate;
	}
	public void setPlate(Plate plate) {
		this.plate = plate;
	}
	public String getVehicleTypeId() {
		return vehicleTypeId;
	}
	public void setVehicleTypeId(String vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}
	public String getVehicleUseId() {
		return vehicleUseId;
	}
	public void setVehicleUseId(String vehicleUseId) {
		this.vehicleUseId = vehicleUseId;
	}
	public String getOrganizationCode() {
		return OrganizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		OrganizationCode = organizationCode;
	}
	public String getVehicleOrganization() {
		return vehicleOrganization;
	}
	public void setVehicleOrganization(String vehicleOrganization) {
		this.vehicleOrganization = vehicleOrganization;
	}
	public String getVehicleIDCard() {
		return vehicleIDCard;
	}
	public void setVehicleIDCard(String vehicleIDCard) {
		this.vehicleIDCard = vehicleIDCard;
	}
	@Override
	public String toString() {
		return "Omc [omcId=" + omcId + ", mdcNorm=" + mdcNorm + ", mdcId=" + mdcId + ", plate=" + plate
				+ ", vehicleTypeId=" + vehicleTypeId + ", vehicleUseId=" + vehicleUseId + ", OrganizationCode="
				+ OrganizationCode + ", vehicleOrganization=" + vehicleOrganization + ", vehicleIDCard=" + vehicleIDCard
				+ "]";
	}
	
	

}
