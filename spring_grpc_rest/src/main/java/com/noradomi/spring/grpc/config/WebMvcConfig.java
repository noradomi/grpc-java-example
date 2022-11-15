package com.noradomi.spring.grpc.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Created by phucvt Date: 15/11/2022 */
public class WebMvcConfig implements WebMvcConfigurer {
  private final long MAX_AGE_SECS = 3600;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
        .maxAge(MAX_AGE_SECS);
  }
}
