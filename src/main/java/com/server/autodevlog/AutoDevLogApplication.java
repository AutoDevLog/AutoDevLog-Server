package com.server.autodevlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class AutoDevLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoDevLogApplication.class, args);
	}

}
