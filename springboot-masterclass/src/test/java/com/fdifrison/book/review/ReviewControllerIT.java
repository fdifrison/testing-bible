package com.fdifrison.book.review;

import com.fdifrison.AbstractIntegrationTest;
import com.fdifrison.book.management.BookRepository;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

class ReviewControllerIT extends AbstractIntegrationTest {

  private static final String ISBN = "9780596004651";

  @Autowired private WebTestClient webTestClient;

  @Autowired private BookRepository bookRepository;

  @Test
  void shouldReturnCreatedReviewWhenBookExistsAndReviewHasGoodQuality() throws JOSEException {}

  @Test
  void shouldReturnReviewStatisticWhenMultipleReviewsForBookFromDifferentUsersExist()
      throws JOSEException {}
}
