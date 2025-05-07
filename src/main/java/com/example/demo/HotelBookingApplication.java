package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class HotelBookingApplication { 

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(HotelBookingApplication.class, args);
		JdbcTemplate template = (JdbcTemplate) context.getBean("JdbcTemplate");
		
		if(template != null) {
			System.out.print("Database connected");
		}
		else {	
			System.out.print("Database not connected");
		}
	
	}

}
