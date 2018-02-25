package com.thea.model;

import java.math.BigDecimal;

public class Quote {

	private String ticker;

	private BigDecimal price;

	public Quote(String ticker, BigDecimal price) {
		this.ticker = ticker;
		this.price = price;
	}

	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Quote [ticker=" + this.ticker + ", price=" + this.price + "]";
	}
}
