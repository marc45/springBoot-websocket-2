package com.thea.model;

import lombok.Data;

@Data
public class Trade {

	private int tradeID;
	private int tradeIdVersion;
	private String tradeType;
	private String tradeSubType;
	private int initDate;
	private final long timestamp;

	public Trade(int tradeID, int tradeIdVersion, String tradeType, String tradeSubType, int initDate) {
		this.tradeID = tradeID;
		this.tradeIdVersion = tradeIdVersion;
		this.tradeType = tradeType;
		this.tradeSubType = tradeSubType;
		this.initDate = initDate;
		this.timestamp = System.currentTimeMillis();
	}

}
