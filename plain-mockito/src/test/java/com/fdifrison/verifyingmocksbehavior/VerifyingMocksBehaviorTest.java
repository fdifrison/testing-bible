package com.fdifrison.verifyingmocksbehavior;

import com.fdifrison.app.*;
import org.instancio.Instancio;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerifyingMocksBehaviorTest {

    // TODO N.B. overuse of verification lead to tight coupling between tests and code, making refactoring much harder..
    //  Hence, DO NOT ABUSE!

    @Mock
    private UserRepository userRepository;

    @Mock
    private BannedUsersClient bannedUsersClient;

    @InjectMocks
    private RegistrationService classUnderTest;

    @Test
    @DisplayName("basic mocks verification")
    void basicMocksVerification() {

        var contactInformation = Instancio.create(ContactInformation.class);
        given(bannedUsersClient.isBanned(eq("fdifrison"), any(Address.class))).willReturn(true);

        assertThatThrownBy(() -> classUnderTest.registerUser("fdifrison", contactInformation))
                .isInstanceOf(IllegalArgumentException.class);

        // TODO with verify we can investigate the mock behavior and also how many time it should have been invoked
        //  with static methods such as times(), never(), atLeastOnce() ...
        verify(bannedUsersClient, atMostOnce()).isBanned("fdifrison", contactInformation.getAddress());
        verify(bannedUsersClient, never()).bannedUserId();
        // TODO with verifyNoMoreInteractions() we are telling mockito that all the planned interaction between the
        //  two mocks have been verified, and to raise an error if it not so
        verifyNoMoreInteractions(bannedUsersClient, userRepository);

    }
}
