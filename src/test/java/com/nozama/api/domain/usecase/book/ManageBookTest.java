package com.nozama.api.domain.usecase.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class ManageBookTest {
  
  @Mock
  private BookRepository repository;

  @InjectMocks
  private ManageBook manageBook;

  @Test
  void Given_NullBook_When_CreatingNewBook_Expect_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> manageBook.create(null));
  }

  @Test
  void When_CreatingNewBook_Expect_BookToBeActive() {
    Book book = new Book();

    when(repository.save(book)).thenReturn(book);
    manageBook.create(book);

    assertEquals(true, book.getActive());
  }

  @Test
  void When_CreatingNewBook_Expect_BookToHaveACreationDate() {
    Book book = new Book();

    when(repository.save(book)).thenReturn(book);
    manageBook.create(book);

    assertInstanceOf(LocalDate.class, book.getCreatedAt());
  }

  @Test
  void Given_AlreadyExistingBookWithInformedSku_When_CreatingBook_Then_ThrowException() {
    
    Book mock = mock(Book.class);

    when(repository.existsBySku(mock.getSku())).thenReturn(true);

    assertThrows(InvalidEntityException.class, () -> manageBook.create(mock));
  }

  @Test
  void Given_AlreadyExistingBookWithInformedIsbn_When_CreatingBook_Then_ThrowException() {
    
    Book mock = mock(Book.class);

    when(repository.existsByIsbn(mock.getIsbn())).thenReturn(true);

    assertThrows(InvalidEntityException.class, () -> manageBook.create(mock));
  }

  @Test
  void Given_NullId_When_FindingById_Expect_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> manageBook.findById(null));
  }

  @Test
  void Given_NoBookWithTheInformedId_When_FindingById_Expect_ThrowException() {
    when(repository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> manageBook.findById(1L));
  }

  @Test
  void Given_NullId_When_UpdatingBook_Expect_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> manageBook.update(null));
  }

  @Test
  void Given_NoBookWithTheInformedId_When_UpdatingBook_Then_ThrowException() {
    Book book = new Book();
    book.setId(1L);

    when(repository.findById(book.getId())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> manageBook.update(book));
  }

  @Test
  void Given_NullId_When_DeletingBook_Expect_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> manageBook.delete(null));
  }

  @Test
  void Given_NoBookWithTheInformedId_When_DeletingBook_Then_ThrowException() {
    when(repository.existsById(1L)).thenReturn(false);
    assertThrows(EntityNotFoundException.class, () -> manageBook.delete(1L));
  }

}
