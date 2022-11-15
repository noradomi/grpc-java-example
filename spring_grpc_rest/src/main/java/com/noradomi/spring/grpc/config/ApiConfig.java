package com.noradomi.spring.grpc.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/** Created by phucvt Date: 15/11/2022 */
@Data
@Configuration
public class ApiConfig {
  @Bean
  public ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
    return new ProtobufJsonFormatHttpMessageConverter();
  }

  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setIncludeHeaders(true);
    filter.setIncludeClientInfo(true);
    return filter;
  }
}
