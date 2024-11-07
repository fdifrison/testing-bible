package com.fdifrison.XXXXadvancedfeatures;

import com.fdifrison.app.BannedUsersClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SpyTest {

    @Spy
    private BannedUsersClient bannedUsersClient;
    // TODO Spies default behavior is to return the concrete implementation on the invocation of the spied class,
    //  however we are still able to stub part or the whole method we are invoking and see the result upon call.
    //  Basically we obtain a partially mocked object
    //  N.B. It is rare to need spies!

    @Test
    @DisplayName("Base spy usage")
    void baseSpyUsage() {
        var result = bannedUsersClient.amountOfBannedAccounts();
        assertThat(result).isEqualTo(42)
                .describedAs("Should return the value hardcoded in the concrete implementation");

        given(bannedUsersClient.amountOfBannedAccounts()).willReturn(500);
        assertThat(bannedUsersClient.amountOfBannedAccounts()).isEqualTo(500)
                .describedAs("Now the spy return the value stabbed");

    }

}
