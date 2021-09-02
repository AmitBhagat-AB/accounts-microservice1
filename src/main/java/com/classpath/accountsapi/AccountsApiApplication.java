package com.classpath.accountsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AccountsApiApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AccountsApiApplication.class);
		//SpringApplication.run(AccountsApiApplication.class, args);
		application.setApplicationStartup(new BufferingApplicationStartup(1500));
		application.run(args);

	}
}
