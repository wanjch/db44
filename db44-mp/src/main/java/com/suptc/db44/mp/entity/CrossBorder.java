package com.suptc.db44.mp.entity;

/**
 * 越界信息
 * 
 * @author wanjingchang
 *
 */
public class CrossBorder {

	int crossBorderType;
	
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
	public int getCrossBorderType() {
		return crossBorderType;
	}

	/**
	 * @param crossBorderType the crossBorderType to set
	 */
	public void setCrossBorderType(int crossBorderType) {
		this.crossBorderType = crossBorderType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CrossBorder [crossBorderType=" + crossBorderType + ", satellite=" + satellite + "]";
	}

	
}
