package com.nozama.api.infrastructure.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
			.antMatchers("/**").permitAll()
			.and()
			.csrf().ignoringAntMatchers("/**")
			.and()
			.headers().frameOptions().disable();

		return http.build();
	}

	// @Bean
	// PasswordEncoder encoder() {
	// 	return new BCryptPasswordEncoder();
	// }
	
}