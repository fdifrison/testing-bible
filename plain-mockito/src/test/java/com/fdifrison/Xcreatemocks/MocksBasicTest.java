package com.fdifrison.Xcreatemocks;

import com.fdifrison.dummy.SimpleRepository;
import com.fdifrison.dummy.SimpleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

class MocksBasicTest {

    @Nested
    class MockWithoutAnnotations {

        private final SimpleRepository repository = Mockito.mock(SimpleRepository.class);
        private final SimpleService service = new SimpleService(repository);

        @Test
        @DisplayName("using Mockito.mock explicitly")
        void shouldPrintTheMockedClass() {
            service.sayHello();
        }
    }

    @Nested
    class MockWithAnnotations {

        @Mock
        private SimpleRepository repository;
        @InjectMocks
        private SimpleService service;

        @BeforeEach
        void setUp() {
            // search for the @Mock annotated objects
            MockitoAnnotations.openMocks(this);
            // TODO we could manually instantiate the service or let @InjectMocks looking for a suitable constructor
            //  service = new SomeService(repository);
        }

        @Test
        @DisplayName("using @Mock annotation with openMocks")
        void shouldPrintTheMockedClass() {
            service.sayHello();
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class MockWithMockExtension {
        @Mock
        private SimpleRepository repository;
        @InjectMocks
        private SimpleService service;

        @Test
        @DisplayName("using @Mock annotation with @ExtendedWith(MockitoExtension.class)")
        void shouldPrintTheMockedClass() {
            service.sayHello();
        }
    }

}