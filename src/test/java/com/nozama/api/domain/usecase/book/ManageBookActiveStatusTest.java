package com.nozama.api.domain.usecase.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class ManageBookActiveStatusTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private ManageBookActiveStatus manageBookActiveStatus;

  @Test
  void When_InactivatingBook_Expect_ActiveEqualsFalse() {
    Book book = new Book();
    book.setId(1L);
    book.setActive(true);
    bookRepository.save(book);
    
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    manageBookActiveStatus.setActive(1L, false);
    
    assertFalse(() -> book.getActive());
  }

  @Test
  void When_ActivatingBook_Expect_ActiveEqualsTrue() {
    Book book = new Book();
    book.setId(1L);
    book.setActive(false);
    bookRepository.save(book);
    
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    manageBookActiveStatus.setActive(1L, true);
    
    assertTrue(() -> book.getActive());
  }

}
