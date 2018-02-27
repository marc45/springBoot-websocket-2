package com.thea.handler;

import java.util.Arrays;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.thea.user.ActiveWebSocketUserRepository;

public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {
	private ActiveWebSocketUserRepository repository;

	public WebSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate,
			ActiveWebSocketUserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		String id = event.getSessionId();
		if (id == null) {
			return;
		}
		this.repository.findById(id).ifPresent(user -> {
			this.repository.deleteById(id);
			this.messagingTemplate.convertAndSend("/topic/friends/signout", Arrays.asList(user.getUsername()));
		});
	}
}
