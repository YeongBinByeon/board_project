package com.cloud.boardgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BoardGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardGatewayApplication.class, args);
	}

}
