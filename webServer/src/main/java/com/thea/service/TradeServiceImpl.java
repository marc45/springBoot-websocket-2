package com.thea.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.thea.model.Trade;

@Service
public class TradeServiceImpl implements TradeService {
	private static List<Trade> trades = new ArrayList<Trade>();

	protected static void initTrades() {
		trades.add(new Trade(1, 1, "Buy", "UserBuy", 20180302));
		trades.add(new Trade(2, 2, "Buy", "TraderBuy", 20180302));
		trades.add(new Trade(3, 3, "Sell", "UserSell", 20180302));
		trades.add(new Trade(4, 4, "Buy", "UserBuy", 20180302));
		trades.add(new Trade(5, 5, "Buy", "TraderBuy", 20180302));
		trades.add(new Trade(6, 6, "Sell", "UserSell", 20180302));
		trades.add(new Trade(7, 7, "Buy", "UserBuy", 20180303));
		trades.add(new Trade(8, 8, "Buy", "TraderBuy", 20180303));
		trades.add(new Trade(9, 9, "Sell", "UserSell", 20180303));
		trades.add(new Trade(10, 10, "Buy", "UserBuy", 20180303));
		trades.add(new Trade(11, 11, "Buy", "TraderBuy", 20180303));
		trades.add(new Trade(12, 12, "Sell", "UserSell", 20180303));
	}

	public List<Trade> getTradeInit() {
		List<Trade> initTrades = new ArrayList<Trade>();
		trades.forEach(trade -> {
			if (trade.getInitDate() == 20180303) {
				initTrades.add(trade);
			}
		});
		return initTrades;
	}

	public List<Trade> getTradeByDate(int dateTime) {
		List<Trade> tradesByDate = new ArrayList<Trade>();
		trades.forEach(trade -> {
			if (trade.getInitDate() == dateTime) {
				tradesByDate.add(trade);
			}
		});
		return tradesByDate;
	}

	@Scheduled(fixedDelay = 1500)
	public List<Trade> updateTradeByDate() {
		List<Trade> tradesUpdate = new ArrayList<Trade>();
		trades.forEach(trade -> {
			if (trade.getInitDate() == 20180303) {
				tradesUpdate.add(trade);
			}
		});
		return tradesUpdate;
	}

	@Scheduled(fixedDelay = 3000)
	private void generateQuotes() {
		if (trades.size() <= 100) {
			trades.add(new Trade(trades.size() + 1, trades.size() + 1, "Buy", "UserBuy", 20180303));
		}
	}

}
