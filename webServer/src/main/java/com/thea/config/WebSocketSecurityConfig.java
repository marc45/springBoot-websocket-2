package com.thea.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages.nullDestMatcher().authenticated()
				.simpSubscribeDestMatchers("/user/queue/errors").permitAll()
				.simpMessageDestMatchers("/queue/**", "/topic/**").denyAll()
				.simpSubscribeDestMatchers("/queue/**/*-user*", "/topic/**/*-user*").denyAll()
				.anyMessage().authenticated();
	}

}
