package com.example.pbs.distanceseri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DistanceseriApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistanceseriApplication.class, args);
	}

}
