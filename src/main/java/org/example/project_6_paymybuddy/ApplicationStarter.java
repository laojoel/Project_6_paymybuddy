package org.example.project_6_paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.project_6_paymybuddy.Controllers", "org.example.project_6_paymybuddy.Services", "org.example.project_6_paymybuddy.Proxies"})
public class ApplicationStarter {
	public static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

	public static void main(String[] args) {
		logger.info("Initializing SafetyNet System");
		SpringApplication.run(ApplicationStarter.class, args);
	}
}
