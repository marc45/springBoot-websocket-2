package com.thea.service;

import java.util.List;

import com.thea.model.Trade;

public interface TradeService {

	List<Trade> getTradeInit();

	List<Trade> getTradeByDate(int dateTime);

	List<Trade> updateTradeByDate();

}
