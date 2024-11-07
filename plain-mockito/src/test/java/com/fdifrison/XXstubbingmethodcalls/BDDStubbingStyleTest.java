package com.fdifrison.XXstubbingmethodcalls;

import com.fdifrison.app.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BDDStubbingStyleTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BannedUsersClient bannedUsersClient;

    @InjectMocks
    private RegistrationService classUnderTest;

    @Test
    @DisplayName("Basic stubbing with BDD style")
    void basicStubbingWithBDDStyle() {
        var user = Instancio.create(User.class).setUsername("fdifrison").setId(null);
        given(bannedUsersClient.isBanned(anyString(), any())).willReturn(false);
        given(userRepository.findByUsername("fdifrison"))
                .willReturn(null);
        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> {
                    var savedUser = invocation.getArgument(0, User.class);
                    savedUser.setId(1L);
                    return savedUser;
                });
        var registered = classUnderTest.registerUser("fdifrison", Instancio.create(ContactInformation.class));
        assertThat(registered).hasFieldOrPropertyWithValue("id", 1L);

    }

}
