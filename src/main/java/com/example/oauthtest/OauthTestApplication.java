package com.example.oauthtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OauthTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthTestApplication.class, args);
	}

}
