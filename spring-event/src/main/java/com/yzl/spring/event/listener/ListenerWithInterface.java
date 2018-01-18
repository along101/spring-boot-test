package com.yzl.spring.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ListenerWithInterface implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("From ListenerWithInterface. Event: ContextRefreshedEvent.");
		System.out.println(event.getApplicationContext().toString());
	}
}
