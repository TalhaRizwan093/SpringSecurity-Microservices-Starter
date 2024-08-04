package com.spring.OAuthSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OAuthSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthSecurityApplication.class, args);
	}

}
