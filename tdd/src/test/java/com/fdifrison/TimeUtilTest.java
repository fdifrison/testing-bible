package com.fdifrison;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Objects;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeUtilTest {

  @Mock private TimeProvider timeProvider;

  @InjectMocks private TimeUtil cut;

  @ParameterizedTest
  @InstancioSource
  void shouldNeverThrowsUnsupportedOperationException(LocalDate randomDate) {
    when(timeProvider.getCurrentTime()).thenReturn(LocalDate.now());
    var exception = catchException(() -> cut.getHumanReadableTimeFormat(randomDate));
    if (!Objects.isNull(exception)) {
      assertThat(exception).isNotInstanceOf(UnsupportedOperationException.class);
    }
  }

  @Test
  void shouldThrowExceptionWhenDateIsInFuture() throws IllegalArgumentException {
    when(timeProvider.getCurrentTime()).thenReturn(LocalDate.now());
    var futureDate = LocalDate.now().plusDays(1);
    assertThatThrownBy(() -> cut.getHumanReadableTimeFormat(futureDate))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Date is in the future");
  }

  @Test
  void shouldReturnTodayWhenDateIsToday() {
    when(timeProvider.getCurrentTime()).thenReturn(LocalDate.now());
    var today = LocalDate.now();
    var result = cut.getHumanReadableTimeFormat(today);
    assertEquals(DisplayDate.TODAY.name(), result);
  }

  @Test
  void shouldReturnMoreThanAYearWhenDateIsBefore365Days() {
    when(timeProvider.getCurrentTime()).thenReturn(LocalDate.now());
    var date = LocalDate.now().minusDays(366);
    var result = cut.getHumanReadableTimeFormat(date);
    assertEquals(DisplayDate.MORE_THAN_A_YEAR.name(), result);
  }

  @Test
  void shouldReturnOneMonthWhenDateIsBefore1Month() {
    when(timeProvider.getCurrentTime()).thenReturn(LocalDate.now());
    var lastMonth = LocalDate.now().minusDays(35);
    var result = cut.getHumanReadableTimeFormat(lastMonth);
    assertEquals(DisplayDate.LAST_MONTH.name(), result);
  }

  @Test
  void shouldReturnMoreThanAMonthWhenDateIsBefore2MonthsButLessThenOneYearAgo() {
    when(timeProvider.getCurrentTime()).thenReturn(LocalDate.now());
    var twoMonthsAgo = LocalDate.now().minusDays(70);
    var result = cut.getHumanReadableTimeFormat(twoMonthsAgo);
    assertEquals(DisplayDate.MORE_THAN_A_MONTH.name(), result);
  }

  @Test
  void shouldReturnYesterdayWhenDateIsBefore1Day() {
    when(timeProvider.getCurrentTime()).thenReturn(LocalDate.now());
    var twoMonthsAgo = LocalDate.now().minusDays(1);
    var result = cut.getHumanReadableTimeFormat(twoMonthsAgo);
    assertEquals(DisplayDate.YESTERDAY.name(), result);
  }
}
