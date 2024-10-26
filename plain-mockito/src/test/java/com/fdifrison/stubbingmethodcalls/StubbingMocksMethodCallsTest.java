package com.fdifrison.stubbingmethodcalls;

import com.fdifrison.app.BannedUsersClient;
import com.fdifrison.app.RegistrationService;
import com.fdifrison.app.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StubbingMocksMethodCallsTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BannedUsersClient bannedUsersClient;

    @InjectMocks
    RegistrationService classUnderTest;

    @Test
    void defaultBehavior() {

    }



}
