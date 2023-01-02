package com.springbootapirestcourse.users;

import com.springbootapirestcourse.users.security.Properties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ApplicationContext applicationContext() {
		return new ApplicationContext();
	}

	@Bean
	public Properties getProperties() {
		return new Properties();
	}

	@Bean public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
