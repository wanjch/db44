package com.suptc.db44.entity;

/**
 * 越界信息
 * 
 * @author wanjingchang
 *
 */
public class FatigueDriving {

	int totalTime;
	
	Satellite satellite;

	public Satellite getSatellite() {
		return satellite;
	}

	public void setSatellite(Satellite s) {
		this.satellite = s;
	}

	/**
	 * @return the crossBorderType
	 */
	public int getTotalTime() {
		return totalTime;
	}

	/**
	 * @param crossBorderType the crossBorderType to set
	 */
	public void setTotalTime(int crossBorderType) {
		this.totalTime = crossBorderType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CrossBorder [crossBorderType=" + totalTime + ", satellite=" + satellite + "]";
	}

	
}
