package com.fdifrison.book.management;

import com.fdifrison.initializer.RSAKeyGenerator;
import com.fdifrison.stubs.OAuth2Stubs;
import com.fdifrison.stubs.OpenLibraryStubs;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

class BookSynchronizationListenerIT {

  @Autowired private WebTestClient webTestClient;

  @Autowired private RSAKeyGenerator rsaKeyGenerator;

  @Autowired private OAuth2Stubs oAuth2Stubs;

  @Autowired private OpenLibraryStubs openLibraryStubs;

  @Autowired private BookRepository bookRepository;

  @Test
  void shouldGetSuccessWhenClientIsAuthenticated() throws JOSEException {}

  @Test
  void shouldReturnBookFromAPIWhenApplicationConsumesNewSyncRequest() {}
}
