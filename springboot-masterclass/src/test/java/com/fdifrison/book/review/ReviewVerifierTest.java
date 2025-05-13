package com.fdifrison.book.review;

import static com.fdifrison.book.review.RandomReviewParameterResolverExtension.RandomReview;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@ExtendWith(RandomReviewParameterResolverExtension.class)
class ReviewVerifierTest {

  private final ReviewVerifier reviewVerifier = new ReviewVerifier();

  @Test
  @DisplayName("Should fail when review contains a swear word")
  void shouldFailWhenReviewContainsSwearWord() {
    var review = "This book is shit you bitch author! it has more than 10 words";
    var result = reviewVerifier.doesMeetQualityStandards(review);
    assertThat(result).withFailMessage("Swearword not detected correctly").isFalse();
  }

  @Test
  @DisplayName("Should fail when review contains (case ignored) 'lorem ipsum'")
  void testLoremIpsum() {
    var review =
        "lorem ipsum dolor sit amet, automatic generated dummy review with more than 10 words";
    var result = reviewVerifier.doesMeetQualityStandards(review);
    assertThat(result).withFailMessage("'Lorem ipsum' not detected correctly").isFalse();
  }

  // TODO to repeat multiple time the same test
  //  We can have different source for the parametrization like @ValueSource, @EnumSource,
  //  @InstancioSource etc..
  @ParameterizedTest
  @CsvFileSource(resources = "/badReview.csv")
  @DisplayName("Should detect bad reviews")
  void shouldFailWhenReviewIsOfBadQuality(String review) {
    var result = reviewVerifier.doesMeetQualityStandards(review);
    assertThat(result).withFailMessage("Bad review not detected").isFalse();
  }

  // TODO example of a test using a custom extension
  @RepeatedTest(5)
  @DisplayName("Should detect bad reviews")
  void shouldFailWhenRandomReviewQualityIsBad(@RandomReview String review) {
    var result = reviewVerifier.doesMeetQualityStandards(review);
    assertThat(result).withFailMessage("Bad review not detected").isFalse();
  }

  @Test
  @DisplayName("Should pass when review is good")
  void shouldPassWhenReviewIsGood() {
    var review = "This book is good, I like it. It has more than 10 words";
    var result = reviewVerifier.doesMeetQualityStandards(review);
    assertThat(result).withFailMessage("Good review not detected").isTrue();
  }
}
