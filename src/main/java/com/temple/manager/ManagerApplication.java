package com.temple.manager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ManagerApplication {
	public static final String APPLICATION_PROPERTIES = "spring.config.location="
			+ "classpath:application.yml,"
			+ "/home/ec2-user/app/config/prod-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(ManagerApplication.class)
				.properties(APPLICATION_PROPERTIES)
				.run(args);
	}

}
