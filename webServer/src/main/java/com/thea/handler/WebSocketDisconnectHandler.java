package com.thea.handler;

import java.util.Arrays;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.thea.user.ActiveWebSocketUser;
import com.thea.user.ActiveWebSocketUserRepository;

public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {
	private ActiveWebSocketUserRepository repository;
	private SimpMessageSendingOperations messageSendingOperations;

	public WebSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate,
			ActiveWebSocketUserRepository repository) {
		super();
		this.repository = repository;
		this.messageSendingOperations = messagingTemplate;
	}

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		String id = event.getSessionId();
		if (id == null) {
			return;
		}
		if (this.repository.findOne(id) != null) {
			ActiveWebSocketUser user = this.repository.findOne(id);
			this.repository.delete(id);
			this.messageSendingOperations.convertAndSend("/topic/friends/signout", Arrays.asList(user.getUsername()));
		}
	}
}
