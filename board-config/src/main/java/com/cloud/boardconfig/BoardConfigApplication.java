package com.cloud.boardconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BoardConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardConfigApplication.class, args);
	}

}
