package com.fdifrison;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Component;

/**
 * Develop a feature to display information when a comment was made (one day ago, 3 days ago, 6
 * month ago, etc.) in a human-readable format: - A comment that is older than 365 days, should
 * return 'more than a year'. - A comment within today should return 'today'. - A date in the future
 * is invalid and should throw an exception.
 */

// TODO see: https://rieckpil.de/course/tsbdr-tdd-example-1-time-utility/
@Component
class TimeUtil {

  private final TimeProvider timeProvider;

  TimeUtil(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }

  public String getHumanReadableTimeFormat(LocalDate creationDate) {

    var timeBetween = Period.between(creationDate, timeProvider.getCurrentTime());

    if (timeBetween.isNegative()) {
      throw new IllegalArgumentException("Date is in the future");
    } else if (timeBetween.getYears() > 0) {
      return DisplayDate.MORE_THAN_A_YEAR.name();
    } else if (timeBetween.getMonths() == 1) {
      return DisplayDate.LAST_MONTH.name();
    } else if (timeBetween.getMonths() > 1) {
      return DisplayDate.MORE_THAN_A_MONTH.name();
    } else if (timeBetween.getDays() == 0) {
      return DisplayDate.TODAY.name();
    } else if (timeBetween.getDays() == 1) {
      return DisplayDate.YESTERDAY.name();
    }
    throw new UnsupportedOperationException("Something went wrong");
  }
}

@Component
class TimeProvider {

  public LocalDate getCurrentTime() {
    return LocalDate.now();
  }
}
