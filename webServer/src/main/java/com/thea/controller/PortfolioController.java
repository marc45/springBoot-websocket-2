package com.thea.controller;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.thea.model.Portfolio;
import com.thea.model.PortfolioPosition;
import com.thea.model.Trade;
import com.thea.service.PortfolioService;
import com.thea.service.TradeService;

@Controller
public class PortfolioController {

	private static final Log logger = LogFactory.getLog(PortfolioController.class);
	private final PortfolioService portfolioService;
	private final TradeService tradeService;

	@Autowired
	public PortfolioController(PortfolioService portfolioService, TradeService tradeService) {
		this.portfolioService = portfolioService;
		this.tradeService = tradeService;
	}

	@SubscribeMapping("/positions")
	public List<PortfolioPosition> getPositions(Principal principal) throws Exception {
		logger.debug("Positions for " + principal.getName());
		Portfolio portfolio = this.portfolioService.findPortfolio(principal.getName());
		return portfolio.getPositions();
	}

	@MessageMapping("/trade")
	public void executeTrade(Trade trade, Principal principal) {
		trade.setUsername(principal.getName());
		logger.debug("Trade: " + trade);
		this.tradeService.executeTrade(trade);
	}

	@SubscribeMapping("/subscribePortfolio")
	@SendTo("/topic/subscribePortfolio")
	public Portfolio getLatestPositionsByName(String name) {
		Portfolio portfolio = this.portfolioService.findPortfolio(name);
		logger.debug("Portfolio is : " + portfolio.toString());
		return portfolio;
	}

	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}

}
