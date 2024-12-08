package com.PBW.RanTreker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RanTrekerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RanTrekerApplication.class, args);
	}

}
