package com.pauloroberto.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.pauloroberto.cursomc.services.DBService;
import com.pauloroberto.cursomc.services.EmailService;
import com.pauloroberto.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dBService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		this.dBService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}