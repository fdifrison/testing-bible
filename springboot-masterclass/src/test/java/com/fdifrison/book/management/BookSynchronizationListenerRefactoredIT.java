package com.fdifrison.book.management;

import com.fdifrison.AbstractIntegrationTest;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

class BookSynchronizationListenerRefactoredIT extends AbstractIntegrationTest {

  @Autowired private WebTestClient webTestClient;

  @Autowired private BookRepository bookRepository;

  @Test
  void shouldGetSuccessWhenClientIsAuthenticated() throws JOSEException {}

  @Test
  void shouldReturnBookFromAPIWhenApplicationConsumesNewSyncRequest() {}
}
