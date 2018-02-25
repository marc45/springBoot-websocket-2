package com.thea.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@MessageMapping("/hi")
	@SendTo("/topic/hi")
	public String handleHi(String incoming) {
		System.out.println("receive message : " + incoming);
		return "hello, " + incoming;
	}

	@SubscribeMapping("/subscribeme")
	public String subscribeThing() {
		System.out.println("subscribe message called.");
		return "thank you subscribe my channel";
	}
}
