package com.thea.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.thea.user.ActiveWebSocketUserRepository;

public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {
	private ActiveWebSocketUserRepository repository;

	public WebSocketDisconnectHandler(ActiveWebSocketUserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		String id = event.getSessionId();
		if (id == null) {
			return;
		}
		if (this.repository.findOne(id) != null) {
			this.repository.delete(id);
		}
	}
}
