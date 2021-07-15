package com.store.apicontents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiContentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiContentsApplication.class, args);
	}

}
