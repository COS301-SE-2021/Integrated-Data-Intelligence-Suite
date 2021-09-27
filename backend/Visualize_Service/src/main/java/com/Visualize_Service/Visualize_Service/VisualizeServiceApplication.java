package com.Visualize_Service.Visualize_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class VisualizeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisualizeServiceApplication.class, args);
	}

}
