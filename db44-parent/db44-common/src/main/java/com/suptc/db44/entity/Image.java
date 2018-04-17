package com.suptc.db44.entity;

import java.util.Arrays;
import java.util.Date;

/**
 * 图片
 * @author wanjingchang
 *
 */
public class Image {
	
	Date snapTime;
	byte[] image;
	String suffix;
	Satellite satellite;
	
	public Date getSnapTime() {
		return snapTime;
	}
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public Satellite getSatellite() {
		return satellite;
	}
	public void setSatellite(Satellite s) {
		this.satellite = s;
	}
	
	@Override
	public String toString() {
		return "Image [snapTime=" + snapTime + ", image=" + Arrays.toString(image) + ", suffix=" + suffix + ", s=" + satellite
				+ "]";
	}
	
}
