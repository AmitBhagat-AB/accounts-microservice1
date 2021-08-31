package com.classpath.accountsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class AccountsApiApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AccountsApiApplication.class);
		//SpringApplication.run(AccountsApiApplication.class, args);
		application.setApplicationStartup(new BufferingApplicationStartup(1500));
		application.run(args);

	}
}
