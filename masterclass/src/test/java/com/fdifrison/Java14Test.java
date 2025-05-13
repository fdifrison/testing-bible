package com.fdifrison;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class Java14Test {

  @Test
  void shouldAllowJava14PreviewFeatures() {
    String json =
        """
      {
        "name":"Duke"
      }
      """;

    assertNotNull(json);
  }
}
