package com.discovery.boardeureca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BoardEurecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardEurecaApplication.class, args);
	}

}
