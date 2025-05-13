package com.fdifrison.book.review;

import java.math.BigDecimal;

public interface ReviewStatistic {
  Long getId();

  Long getRatings();

  String getIsbn();

  BigDecimal getAvg();
}
