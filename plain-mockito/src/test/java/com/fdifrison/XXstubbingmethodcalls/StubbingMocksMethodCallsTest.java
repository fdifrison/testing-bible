package com.fdifrison.XXstubbingmethodcalls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fdifrison.app.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
public class StubbingMocksMethodCallsTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BannedUsersClient bannedUsersClient;

    @InjectMocks
    RegistrationService classUnderTest;

    @Test
    @DisplayName("Mock methods default behavior if not stubbed")
    void defaultBehavior() {
        // TODO If mocks behavior is not stubbed, mockito will fall back to null for objects and the default value for
        // primitives
        var user = userRepository.findByUsername("fdifrison");
        assertThat(user).isNull();
        var saved = userRepository.save(Instancio.of(User.class).create());
        assertThat(saved).isNull();
        var bannedCount = bannedUsersClient.amountOfBannedAccounts();
        assertThat(bannedCount).isZero();
        var bannedList = bannedUsersClient.bannedUserId();
        assertThat(bannedList).isEmpty();
        var banRate = bannedUsersClient.banRate();
        assertThat(banRate).isEqualTo(0.0);
    }

    @Nested
    @MockitoSettings(strictness = Strictness.LENIENT)
    class BasicStubbing {

        @Test
        @DisplayName("Strictness mode required to lenient")
        void basicStubbingLenient() {
            // TODO Since we are not using the stubbing behavior mockito would throw an UnnecessaryStubbingException, to
            //  avoid this we need to tweak the default Mockito settings for the strictness value to LENIENT
            var address = Instancio.of(Address.class).create();
            when(bannedUsersClient.isBanned("fdifrison", address)).thenReturn(true);
        }

        @Test
        @DisplayName("Correct basic stubbing")
        void basicStubbing() {
            var address = Instancio.of(Address.class).create();
            when(bannedUsersClient.isBanned("fdifrison", address)).thenReturn(true);
            var isUserBanned = bannedUsersClient.isBanned("fdifrison", address);
            assertThat(isUserBanned).isTrue();
        }

        @Test
        @DisplayName("Stubbing with ArgumentMatcher")
        void stubbingWithArgumentMatcher() {
            // TODO We can leave the test implementation more generic by using some Mockito specific tools like
            // argumentMatcher
            //  the limitation is that we need to use it for all the argument of the mocked class' method
            //  we can also define custom argumentsMatcher with argThat()
            when(bannedUsersClient.isBanned(anyString(), any(Address.class))).thenReturn(true);
            var isUserBanned = bannedUsersClient.isBanned("fdifrison", new Address());
            assertThat(isUserBanned).isTrue();
        }

        @Test
        @DisplayName("Throwing exceptions from mocks with thenThrow()")
        void stubbingWithExceptions() {
            when(bannedUsersClient.isBanned(any(), any())).thenThrow(new RuntimeException("An exception"));
            assertThatThrownBy(() -> bannedUsersClient.isBanned("fdifrison", new Address()))
                    .hasMessage("An exception");
        }

        @Test
        @DisplayName("Calling the real method from the stub with thenCallRealMethod()")
        void stubbingWithRealMethod() {
            // TODO Even if the client is mocked we can still instruct mockito to use the real implementation of the
            // method
            //  we are calling in the stubbing
            when(bannedUsersClient.isBanned(eq("fdifrison"), any(Address.class)))
                    .thenCallRealMethod();
            var isUserBanned = bannedUsersClient.isBanned("fdifrison", new Address());
            assertThat(isUserBanned).isFalse();
        }

        @Test
        @DisplayName("Stubbing with thenAnswer()")
        void stubbingWithThenAnswer() {
            // TODO to have more control on the conditional response of the stubs we can specify a custom answer to the
            // method call
            when(bannedUsersClient.isBanned(eq("fdifrison"), any(Address.class)))
                    .thenAnswer(invocation -> {
                        var address = invocation.getArgument(1, Address.class);
                        return address.getCity() != null ? true : new RuntimeException("An exception");
                    });
            assertThatThrownBy(() -> bannedUsersClient.isBanned("fdifrison", new Address()))
                    .isInstanceOf(RuntimeException.class);

            // TODO One practical example could be to intercept the db auto-generation of the identity column
            when(userRepository.save(argThat(user -> user.getUsername().equals("fdifrison"))))
                    .thenAnswer(invocation -> {
                        var user = invocation.getArgument(0, User.class);
                        user.setId(1L);
                        return user;
                    });

            var user = userRepository.save(Instancio.of(User.class)
                    .set(field(User::getUsername), "fdifrison")
                    .create());

            assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class ClassUnderTest {

        @Test
        @DisplayName("Banned user is not registered")
        void shouldNotRegisterBannedUser() {
            when(bannedUsersClient.isBanned(eq("fdifrison"), any(Address.class)))
                    .thenReturn(true);
            assertThatThrownBy(
                            () -> classUnderTest.registerUser("fdifrison", Instancio.create(ContactInformation.class)))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("Happy path: allow registration")
        void shouldAllowRegistration() {
            when(bannedUsersClient.isBanned(eq("fdifrison"), any(Address.class)))
                    .thenReturn(false);
            when(userRepository.findByUsername(eq("fdifrison"))).thenReturn(null);
            when(userRepository.save(argThat(user -> user.getUsername().equals("fdifrison"))))
                    .thenAnswer(invocation -> {
                        var user = invocation.getArgument(0, User.class);
                        user.setId(1L);
                        return user;
                    });

            var user = classUnderTest.registerUser("fdifrison", Instancio.create(ContactInformation.class));
            assertThat(user)
                    .isNotNull()
                    .hasFieldOrPropertyWithValue("id", 1L)
                    .hasFieldOrPropertyWithValue("username", "fdifrison");
        }
    }
}
