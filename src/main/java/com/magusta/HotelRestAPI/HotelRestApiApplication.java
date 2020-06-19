package com.magusta.HotelRestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//Anotacja
@EnableJpaAuditing
public class HotelRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRestApiApplication.class, args);
	}

}