package com.fdifrison.XXXverifyingmocksbehavior;

import com.fdifrison.app.*;
import com.fdifrison.util.UserAssert;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

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

    @Test
    @DisplayName("Verifying in order")
    void verifyingInOrder() {
        var user = Instancio.create(User.class).setUsername("fdifrison").setId(null);
        var saved = userRepository.save(user);
        given(bannedUsersClient.isBanned(anyString(), any())).willReturn(false);
        given(userRepository.findByUsername("fdifrison"))
                .willReturn(saved);
        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> {
                    var savedUser = invocation.getArgument(0, User.class);
                    savedUser.setId(1L);
                    return savedUser;
                });

        var registered = classUnderTest.registerUser("fdifrison", Instancio.create(ContactInformation.class));

        // TODO we can instruct mockito to check also the order of execution of the stubbing under test
        var inOrder = inOrder(userRepository, bannedUsersClient);
        inOrder.verify(bannedUsersClient).isBanned(anyString(), any());
        inOrder.verify(userRepository).findByUsername("fdifrison");
        inOrder.verify(userRepository).save(any(User.class));
        inOrder.verifyNoMoreInteractions();

        UserAssert.assertThat(registered).hasUsername("fdifrison").isNotNull();

    }

    @Test
    @DisplayName("Verifying with argumentsCaptor")
    void verifyingWithArgumentsCaptor() {
        var contactInformation = Instancio.create(ContactInformation.class);
        given(bannedUsersClient.isBanned(anyString(), any())).willReturn(false);
        given(userRepository.findByUsername("fdifrison")).willReturn(null);
        given(userRepository.save(any(User.class))).willReturn(new User().setUsername("fdifrison").setEmail(contactInformation.getEmail()));
        var registered = classUnderTest.registerUser("fdifrison", contactInformation);

        // TODO argumentsCaptors allow us to investigate the objects used or returned by the mocks during the verify phase
        verify(userRepository).save(userArgumentCaptor.capture());
        var capturedUser = userArgumentCaptor.getValue();
        assertThat(registered.getEmail()).isEqualTo(capturedUser.getEmail());
    }

}
