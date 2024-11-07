package com.fdifrison.XXstubbingmethodcalls;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

import com.fdifrison.app.EventNotifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StubbingVoidMethodsTest {

    @Mock
    private EventNotifier notifier;

    @Test
    @DisplayName("Stubbing a void method")
    void testVoidMethod() {
        // TODO If the method under test return void we need to use a different approach; first we define the behavior
        // and
        //  after on which method call
        doThrow(new RuntimeException()).when(notifier).notifyNewUserCreation(anyString());
        assertThatThrownBy(() -> notifier.notifyNewUserCreation("fdifrison")).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Multiple chained call on void method with different behavior")
    void testVoidMethodWithDifferentBehavior() {
        doThrow(new RuntimeException("Error on second call"))
                .doNothing()
                .when(notifier)
                .notifyNewUserCreation(anyString());
        assertThatThrownBy(() -> notifier.notifyNewUserCreation("fdifrison"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error on second call");
        notifier.notifyNewUserCreation("fdifrison");
    }
}
