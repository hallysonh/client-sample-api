package com.halmeida.clientapi;

import com.halmeida.clientapi.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class ClientApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientApiApplication.class, args);
	}
}
