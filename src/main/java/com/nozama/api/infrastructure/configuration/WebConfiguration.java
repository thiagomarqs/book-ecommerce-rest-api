package com.nozama.api.infrastructure.configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Value("${cors.allowed-origins:*}")
  private String corsAllowedOrigins = "";

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    Map<String, MediaType> mediaTypes = Map.of(
        "json", MediaType.APPLICATION_JSON,
        "xml", MediaType.APPLICATION_XML);

    configurer
        .favorParameter(false)
        .ignoreAcceptHeader(false)
        .defaultContentType(MediaType.APPLICATION_JSON)
        .mediaTypes(mediaTypes);
  }

  @Override
    public void addCorsMappings(CorsRegistry registry) {    
      registry
        .addMapping("/api/**")
        .allowedOriginPatterns(corsAllowedOrigins)
        .allowedMethods("*")
        .allowCredentials(true);
    }

}
