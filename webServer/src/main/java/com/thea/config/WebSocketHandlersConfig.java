package com.thea.config;

import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thea.handler.WebSocketConnectHandler;
import com.thea.handler.WebSocketDisconnectHandler;
import com.thea.user.ActiveWebSocketUserRepository;

@Configuration
public class WebSocketHandlersConfig<S extends Session> {

	@Bean
	public WebSocketConnectHandler<S> webSocketConnectHandler(ActiveWebSocketUserRepository repository) {
		return new WebSocketConnectHandler<S>(repository);
	}

	@Bean
	public WebSocketDisconnectHandler<S> webSocketDisconnectHandler(ActiveWebSocketUserRepository repository) {
		return new WebSocketDisconnectHandler<S>(repository);
	}
}
