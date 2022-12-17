package com.nozama.api.infrastructure.web;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultWebConfiguration implements WebMvcConfigurer {

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

}
