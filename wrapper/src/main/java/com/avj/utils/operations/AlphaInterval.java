package com.avj.utils.operations;

public enum AlphaInterval {
	ONE_MINUTE("1min"),
	FIVE_MINUTES("5min"),
	FIFTEEN_MINUTES("15min"),
	THIRTY_MINUTES("30min"),
	SIXTY_MINUTES("60min"),
	DAILY("daily"),
	WEEKLY("weekly"),
	MONTHLY("monthly");
	
	private final String interval;

	private AlphaInterval(String interval) {
		this.interval = interval;
	}
	
	@Override
	public String toString() {
		return this.interval;
	}
	
}
