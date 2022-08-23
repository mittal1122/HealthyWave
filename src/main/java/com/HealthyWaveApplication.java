package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthyWaveApplication {

	final static Logger logger = LoggerFactory.getLogger(HealthyWaveApplication.class);
	
	public static void main(String[] args) {
	
		logger.info("info :-> hey server is started? ");
		logger.error("error :-> port error found ..... Please correct....");
		SpringApplication.run(HealthyWaveApplication.class, args);
	}

}
