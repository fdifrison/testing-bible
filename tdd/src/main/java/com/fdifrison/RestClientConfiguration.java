package com.fdifrison;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

  private final String dummyJsonUrl;

  public RestClientConfiguration(@Value("clients.dummyJson") String dummyJsonUrl) {
    this.dummyJsonUrl = dummyJsonUrl;
  }

  @Bean
  RestClient dummyJsonClient() {
    return RestClient.builder().baseUrl(dummyJsonUrl).build();
  }
}
