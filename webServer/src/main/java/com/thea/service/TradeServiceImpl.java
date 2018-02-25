package com.thea.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.thea.model.Portfolio;
import com.thea.model.PortfolioPosition;
import com.thea.model.Trade;
import com.thea.model.Trade.TradeAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TradeServiceImpl implements TradeService {
	private static final Log logger = LogFactory.getLog(TradeServiceImpl.class);
	private final SimpMessageSendingOperations messagingTemplate;
	private final PortfolioService portfolioService;
	private final List<TradeResult> tradeResults = new CopyOnWriteArrayList<>();

	@Autowired
	public TradeServiceImpl(SimpMessageSendingOperations messagingTemplate, PortfolioService portfolioService) {
		this.messagingTemplate = messagingTemplate;
		this.portfolioService = portfolioService;
	}

	/**
	 * In real application a trade is probably executed in an external system, i.e.
	 * asynchronously.
	 */
	public void executeTrade(Trade trade) {
		Portfolio portfolio = this.portfolioService.findPortfolio(trade.getUsername());
		String ticker = trade.getTicker();
		int sharesToTrade = trade.getShares();

		PortfolioPosition newPosition = (trade.getAction() == TradeAction.Buy) ? portfolio.buy(ticker, sharesToTrade)
				: portfolio.sell(ticker, sharesToTrade);

		if (newPosition == null) {
			String payload = "Rejected trade " + trade;
			this.messagingTemplate.convertAndSendToUser(trade.getUsername(), "/queue/errors", payload);
			return;
		}

		this.tradeResults.add(new TradeResult(trade.getUsername(), newPosition));
	}

	@Scheduled(fixedDelay = 1500)
	public void sendTradeNotifications() {
		Map<String, Object> map = new HashMap<>();
		map.put(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);

		for (TradeResult result : this.tradeResults) {
			if (System.currentTimeMillis() >= (result.timestamp + 1500)) {
				logger.debug("Sending position update: " + result.position);
				this.messagingTemplate.convertAndSendToUser(result.user, "/queue/position-updates", result.position,
						map);
				this.tradeResults.remove(result);
			}
		}
	}

	private static class TradeResult {
		private final String user;
		private final PortfolioPosition position;
		private final long timestamp;

		public TradeResult(String user, PortfolioPosition position) {
			this.user = user;
			this.position = position;
			this.timestamp = System.currentTimeMillis();
		}
	}

}
