package com.pkm.snork.capture.cutoffdate;

import java.util.Date;

import org.joda.time.DateTime;

public class CutoffDateRequest {

	private DateTime cutoffTime;

	/**
	 * @param cutoffTime
	 */
	public CutoffDateRequest() {
		this.cutoffTime = null;
	}

	/**
	 * @return the cutoffTime
	 */
	public DateTime getCutoffTime() {
		return cutoffTime;
	}

	/**
	 * @param cutoffTime
	 *            the cutoffTime to set
	 */
	public void setCutoffTime(DateTime cutoffTime) {
		this.cutoffTime = cutoffTime;
	}

	/**
	 * @param cutoffTime
	 *            the cutoffTime to set
	 */
	public void setCutoffTime(Date cutoffTime) {
		this.cutoffTime = new DateTime(cutoffTime);
	}
	
}
