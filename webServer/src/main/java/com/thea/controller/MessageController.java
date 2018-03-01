package com.thea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.thea.security.CurrentUser;
import com.thea.user.ActiveWebSocketUserRepository;
import com.thea.user.InstantMessage;
import com.thea.user.SysUser;

@Controller
public class MessageController {
	private SimpMessageSendingOperations messagingTemplate;
	private ActiveWebSocketUserRepository activeUserRepository;

	@Autowired
	public MessageController(ActiveWebSocketUserRepository activeUserRepository,
			SimpMessageSendingOperations messagingTemplate) {
		this.activeUserRepository = activeUserRepository;
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/im")
	public void im(InstantMessage im, @CurrentUser SysUser currentUser) {
		im.setFrom(currentUser.getEmail());
		this.messagingTemplate.convertAndSendToUser(im.getTo(), "/queue/messages", im);
		this.messagingTemplate.convertAndSendToUser(im.getFrom(), "/queue/messages", im);
	}

	@SubscribeMapping("/users")
	public List<String> subscribeMessages() throws Exception {
		return this.activeUserRepository.findAllActiveUsers();
	}
}
