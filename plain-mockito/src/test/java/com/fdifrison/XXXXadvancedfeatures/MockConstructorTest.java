package com.fdifrison.XXXXadvancedfeatures;

import com.fdifrison.app.JpaUserRepository;
import com.fdifrison.app.User;
import com.fdifrison.util.UserAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockConstructorTest {

    // TODO N.B. this feature is only relevant if we are not using a DI framework

    @Test
    @DisplayName("Mocking the constructor call")
    void mockConstructorCall() {

        // TODO we can instruct mockito to mock all the constructor calls of a class in a try-with-resources block;
        //  calling a method of the object from outside the scope will invoke the concrete implementation, we would then
        //  need to mock the whole object to be able to stub its methods.
        try (var mockedConstructor = mockConstruction(JpaUserRepository.class,
                // We can add additional stubbing
                (mock, context) -> {
                    given(mock.findByUsername("fdifrison")).willReturn(new User().setUsername("fdifrison"));
                    given(mock.save(any(User.class))).willReturn(new User().setUsername("fdifrison"));
                })) {

            var repository = new JpaUserRepository(); // now the class is mocked
            var user = repository.save(new User());
            var retrieved = repository.findByUsername("fdifrison");

            Mockito.verify(repository).findByUsername("fdifrison");
            UserAssert.assertThat(user).hasUsername("fdifrison");
            UserAssert.assertThat(user).hasUsername(retrieved.getUsername());

            // TODO with constructed() we can get a reference to the mock
            var constructedMock = mockedConstructor.constructed();
            assertThat(constructedMock).hasSize(1);

        }
    }


}
