package com.fdifrison.XXXXadvancedfeatures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

import com.fdifrison.app.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockStaticMethodTest {

    // TODO To create mocks mockito use the MockMaker interface from the bytebuddy library and the default behavior is
    // subclassing.
    //  To be able to stub static method, mockito need a different mocking strategy called "inline" (see in pom
    // mockito-inline).

    @Mock
    private UserRepository userRepository;

    @Mock
    private BannedUsersClient bannedUsersClient;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ContactInformation contactInformation;

    @InjectMocks
    private RegistrationService classUnderTest;

    private final LocalDateTime defaultDate = LocalDateTime.of(2020, 3, 25, 0, 0);

    @Test
    @DisplayName("Mocking a static method call")
    void mockStaticMethodCalls() {

        try (var mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
            // TODO the static mock implementation is thread local, therefore, outside the try-with resources the
            // LocalDateTime
            //  class will retain its normal behavior
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(defaultDate);

            given(bannedUsersClient.isBanned(anyString(), any())).willReturn(false);
            given(userRepository.findByUsername("fdifrison")).willReturn(null);
            given(userRepository.save(any(User.class))).willAnswer(invocation -> {
                var user = invocation.getArgument(0, User.class);
                user.setId(1L);
                return user;
            });

            var registered = classUnderTest.registerUser("fdifrison", contactInformation);

            assertThat(registered.getCreatedAt()).isEqualTo(defaultDate);
        }

        assertThat(LocalDateTime.now()).isNotEqualTo(defaultDate);
    }
}
