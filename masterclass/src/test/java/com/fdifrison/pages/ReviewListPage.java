package com.fdifrison.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;

public class ReviewListPage {

  public ReviewListPage shouldContainExactlyOneReview(String reviewTitle, String reviewContent) {
    $("#all-reviews").click();
    $("#reviews").should(Condition.appear);
    $$("#reviews > div").shouldHave(CollectionCondition.size(1));
    $("#review-0 .review-title").shouldHave(Condition.text(reviewTitle));
    $("#review-0 .review-content").shouldHave(Condition.text(reviewContent));
    return this;
  }
}
