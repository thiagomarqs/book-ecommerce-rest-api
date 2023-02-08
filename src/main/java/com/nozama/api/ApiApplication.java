package com.nozama.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Nozama",
		description = "Nozama is a fictional online book retailer. This API provides functionalities for the admins to manage the e-commerce and for the users to view, register and purchase the books.",
		contact = @Contact(
			name = "Thiago da Silva",
			email = "thiagodsmarques@hotmail.com",
			url = "https://github.com/thiagomarqs"
		)
	)
)
public class ApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
