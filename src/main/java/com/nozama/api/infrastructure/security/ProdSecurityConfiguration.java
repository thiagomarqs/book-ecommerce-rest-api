package com.nozama.api.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nozama.api.application.filter.JwtFilter;
import com.nozama.api.domain.enums.RoleName;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("prod")
public class ProdSecurityConfiguration {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
				.antMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
				.antMatchers("/api/customers/**").hasAnyRole(RoleName.CUSTOMER.name(), RoleName.ADMIN.name())
				.antMatchers(HttpMethod.GET).permitAll()
				.anyRequest().hasRole(RoleName.ADMIN.name())
			.and()
				.csrf().ignoringAntMatchers("/**")
			.and()
				.headers().frameOptions().disable()
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}