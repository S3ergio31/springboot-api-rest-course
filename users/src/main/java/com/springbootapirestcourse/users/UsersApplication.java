package com.springbootapirestcourse.users;

import com.springbootapirestcourse.users.security.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UsersApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(UsersApplication.class);
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

}
