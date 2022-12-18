package com.nozama.api.infrastructure.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("prod")
public class ProdWebConfiguration implements WebMvcConfigurer {
  
  @Value("${cors.allowed-origins:*}")
  private String corsAllowedOrigins = "";

  @Override
  public void addCorsMappings(CorsRegistry registry) {    
    
    registry
      .addMapping("/api/**")
      .allowedOriginPatterns(corsAllowedOrigins)
      .allowedMethods("*")
      .allowCredentials(true);
  }

}
