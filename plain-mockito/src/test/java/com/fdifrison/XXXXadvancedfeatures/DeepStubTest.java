package com.fdifrison.XXXXadvancedfeatures;

import com.fdifrison.app.Address;
import com.fdifrison.app.ContactInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DeepStubTest {

    // TODO Deep stubs are used to return inner mocks from a mock; basically, if we have a chain of inner objects inside
    //  a mock that need to be called to reach the method under test we have two option: explicitly stub the behavior of
    //  each element in the chain, or directly leave mockito do the nested stubbing by telling him that the first mock
    //  in the chain has to be deeply stubbed.

    @Test
    @DisplayName("Without deepStub")
    void withoutDeepStub() {

        // TODO Address is a field to the ContactInformation class, therefore, if we want to stub its behavior starting
        //  from the parent class we need to mock both.

        var contactInfo = mock(ContactInformation.class);
        var address = mock(Address.class);

        given(contactInfo.getAddress()).willReturn(address);
        given(address.getCity()).willReturn("Stormwind");

        assertThat(contactInfo.getAddress().getCity()).isEqualTo("Stormwind");

    }

    @Test
    @DisplayName("With deepStub")
    void withDeepStub() {

        var contactInfo = mock(ContactInformation.class, Answers.RETURNS_DEEP_STUBS);
        given(contactInfo.getAddress().getCity()).willReturn("Stormwind");
        assertThat(contactInfo.getAddress().getCity()).isEqualTo("Stormwind");

    }
}
