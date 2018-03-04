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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thea.model.Trade;
import com.thea.service.TradeService;

@Controller
public class TradeController {

	private static final Log logger = LogFactory.getLog(TradeController.class);
	private final TradeService tradeService;

	@Autowired
	public TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@RequestMapping("/restApi/init")
	@ResponseBody
	public List<Trade> getTradesInit(Principal principal) {
		logger.debug("Positions for " + principal.getName());
		return tradeService.getTradeInit();
	}

	@MessageMapping("/tradesBydate")
	@SendTo("/topic/tradesBydate")
	public List<Trade> getTradesByDate(Principal principal, String dateTime) {
		logger.debug("Get Trades By date: " + principal.getName());
		int date = Integer.parseInt(dateTime);
		return tradeService.getTradeByDate(date);
	}

	@SubscribeMapping("/subscribeTrades")
	@SendTo("/topic/subscribeTrades")
	public List<Trade> updateLatestTradesByDate(String dateTime) {
		logger.debug("Update latest trades by date starts... date is : " + dateTime);
		return tradeService.updateTradeByDate();
	}

	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}

}
