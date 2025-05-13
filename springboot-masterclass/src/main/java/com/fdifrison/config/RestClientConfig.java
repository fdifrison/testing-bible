package com.fdifrison.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient openLibraryWebClient(
      @Value("${clients.open-library.base-url}") String openLibraryBaseUrl,
      RestClient.Builder restClientBuilder) {

    return restClientBuilder.baseUrl(openLibraryBaseUrl).requestFactory(requestFactory()).build();
  }

  private ClientHttpRequestFactory requestFactory() {
    var factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(2000);
    factory.setReadTimeout(2000);
    return factory;
  }
}
