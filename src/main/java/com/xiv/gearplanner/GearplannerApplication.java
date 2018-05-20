package com.xiv.gearplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GearplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GearplannerApplication.class, args);
	}
}
