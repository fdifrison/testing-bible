package com.fdifrison.book.review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fdifrison.book.management.BookRepository;
import com.fdifrison.book.management.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

  @Mock private ReviewVerifier mockedReviewVerifier;

  @Mock private UserService userService;

  @Mock private BookRepository bookRepository;

  @Mock private ReviewRepository reviewRepository;

  @InjectMocks private ReviewService cut;

  private static final String EMAIL = "duke@spring.io";
  private static final String USERNAME = "duke";
  private static final String ISBN = "42";

  @Test
  void shouldNotBeNull() {}

  @Test
  @DisplayName("Write english sentence")
  void shouldThrowExceptionWhenReviewedBookIsNotExisting() {}

  @Test
  void shouldRejectReviewWhenReviewQualityIsBad() {}

  @Test
  void shouldStoreReviewWhenReviewQualityIsGoodAndBookIsPresent() {}
}
