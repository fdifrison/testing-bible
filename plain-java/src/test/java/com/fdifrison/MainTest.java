package com.fdifrison;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    User user;

    @Test
    void doNothing() {
        Assertions.assertThat(user).isNotNull();
    }
}
