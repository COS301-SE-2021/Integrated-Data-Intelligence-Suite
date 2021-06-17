package com.Parse_Service.Parse_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ParseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParseServiceApplication.class, args);
	}

}
