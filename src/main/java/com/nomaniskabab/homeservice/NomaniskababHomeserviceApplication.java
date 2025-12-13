package com.nomaniskabab.homeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class NomaniskababHomeserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NomaniskababHomeserviceApplication.class, args);
	}

}
