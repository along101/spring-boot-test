package com.yzl.spring.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ActuatorTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActuatorTestApplication.class, args);
	}
}
