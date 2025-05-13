package com.fdifrison.book.management;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(OpenLibraryRestTemplateApiClient.class)
class OpenLibraryRestTemplateApiClientTest {

  @Autowired private OpenLibraryRestTemplateApiClient cut;

  @Autowired private MockRestServiceServer mockRestServiceServer;

  private static final String ISBN = "9780596004651";

  @Test
  void shouldInjectBeans() {}

  @Test
  void shouldReturnBookWhenResultIsSuccess() {}

  @Test
  void shouldReturnBookWhenResultIsSuccessButLackingAllInformation() {}

  @Test
  void shouldPropagateExceptionWhenRemoteSystemIsDown() {}

  @Test
  void shouldContainCorrectHeadersWhenRemoteSystemIsInvoked() {}
}
