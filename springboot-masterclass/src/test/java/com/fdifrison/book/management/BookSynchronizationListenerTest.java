package com.fdifrison.book.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookSynchronizationListenerTest {

  private static final String VALID_ISBN = "1234567891234";

  @Mock private BookRepository bookRepository;

  @Mock private OpenLibraryApiClient openLibraryApiClient;

  @InjectMocks private BookSynchronizationListener cut;

  // TODO capture a method argument of type book to use as verification for the repository .save()
  //  call
  @Captor private ArgumentCaptor<Book> bookArgumentCaptor;

  @Test
  void shouldRejectBookWhenIsbnIsMalformed() {
    var bookSynchronization = new BookSynchronization("11");
    cut.consumeBookUpdates(bookSynchronization);
    verifyNoInteractions(openLibraryApiClient, bookRepository);
  }

  @Test
  void shouldNotOverrideWhenBookAlreadyExists() {
    var bookSynchronization = new BookSynchronization(VALID_ISBN);
    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(new Book());
    cut.consumeBookUpdates(bookSynchronization);
    verifyNoInteractions(openLibraryApiClient);
    verify(bookRepository, times(0)).save(any());
  }

  @Test
  void shouldThrowExceptionWhenProcessingFails() {
    var bookSynchronization = new BookSynchronization(VALID_ISBN);
    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(null);
    when(openLibraryApiClient.fetchMetadataForBook(VALID_ISBN)).thenThrow(RuntimeException.class);
    Assertions.assertThatThrownBy(() -> cut.consumeBookUpdates(bookSynchronization))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  void shouldStoreBookWhenNewAndCorrectIsbn() {
    var book = Instancio.create(Book.class).setIsbn(VALID_ISBN).setId(null);
    var bookSynchronization = new BookSynchronization(VALID_ISBN);
    when(bookRepository.findByIsbn(VALID_ISBN)).thenReturn(null);
    when(openLibraryApiClient.fetchMetadataForBook(VALID_ISBN)).thenReturn(book);
    when(bookRepository.save(book))
        .then(
            invocation -> {
              Book argument = invocation.getArgument(0);
              argument.setId(1L);
              return argument;
            });
    cut.consumeBookUpdates(bookSynchronization);
    verify(bookRepository, times(1)).save(book);

    // TODO with this we capture the same instance of the book returned by the mocking of the
    //  repository so that we can use it for assertions later
    verify(bookRepository).save(bookArgumentCaptor.capture());
    var savedBook = bookArgumentCaptor.getValue();
    assertThat(savedBook.getIsbn()).isEqualTo(VALID_ISBN);
    assertThat(savedBook.getId()).isEqualTo(1L);
  }
}
