package com.thea.handler;

import java.security.Principal;
import java.util.Calendar;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.thea.user.ActiveWebSocketUser;
import com.thea.user.ActiveWebSocketUserRepository;

public class WebSocketConnectHandler<S> implements ApplicationListener<SessionConnectEvent> {
	private ActiveWebSocketUserRepository repository;

	public WebSocketConnectHandler(ActiveWebSocketUserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		Principal user = SimpMessageHeaderAccessor.getUser(headers);
		if (user == null) {
			return;
		}
		String id = SimpMessageHeaderAccessor.getSessionId(headers);
		this.repository.save(new ActiveWebSocketUser(id, user.getName(), Calendar.getInstance()));
	}
}
