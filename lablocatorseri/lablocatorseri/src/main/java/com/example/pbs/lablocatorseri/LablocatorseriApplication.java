package com.example.pbs.lablocatorseri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LablocatorseriApplication {

	public static void main(String[] args) {

		SpringApplication.run(LablocatorseriApplication.class, args);
	}

}
