package com.luv2code.pbl4.jobseekerapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class JobseekerapplicationApplication {

	public static void main(String[] args) {

		SpringApplication.run(JobseekerapplicationApplication.class, args);
	}

}
