package com.thea.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import com.thea.model.Trade;
import com.thea.service.TradeService;
import com.thea.user.ActiveWebSocketUserRepository;

@Controller
public class TradeController {

	private static final Log logger = LogFactory.getLog(TradeController.class);
	private final TradeService tradeService;
	private SimpMessageSendingOperations messagingTemplate;
	private ActiveWebSocketUserRepository activeUserRepository;

	@Autowired
	public TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@MessageMapping("/tradesBydate")
	@SendTo("/topic/subscribeTrades")
	public List<Trade> getTradesByDate(String dateTime) {
		logger.debug("Get Trades By date: " + dateTime);
		int date = Integer.parseInt(dateTime);
		return tradeService.getTradeByDate(date);
	}

	@SubscribeMapping("/subscribeTrades")
	@SendTo("/topic/subscribeTrades")
	public List<Trade> updateLatestTradesByDate(String dateTime) {
		logger.debug("Update latest trades by date starts... date is : " + dateTime);
		return tradeService.updateTradeByDate();
	}

	@SubscribeMapping("/users")
	public List<String> subscribeMessages() throws Exception {
		return this.activeUserRepository.findAllActiveUsers();
	}

	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}

}
