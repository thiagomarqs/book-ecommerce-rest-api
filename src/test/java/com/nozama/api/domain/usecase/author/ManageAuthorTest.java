package com.nozama.api.domain.usecase.author;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Author;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class ManageAuthorTest {

  @Mock
  private AuthorRepository repository;

  @InjectMocks
  private ManageAuthor manageAuthor;

  @Test
  void Given_NullAuthor_When_CreatingAuthor_Then_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> manageAuthor.create(null));
  }

  @Test
  void Given_AlreadyExistingAuthorName_When_CreatingAuthor_Then_ThrowException() {
    
    Author author = new Author(1L, "Test");
    
    when(repository.existsByName("Test")).thenReturn(true);

    assertThrows(InvalidEntityException.class, () -> manageAuthor.create(author));

  }

  @Test
  void Given_NullAuthorId_When_FindingAuthor_Then_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> manageAuthor.findById(null));
  }

  @Test
  void Given_InexistingAuthorId_When_FindingById_Then_ThrowException() {

    when(repository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> manageAuthor.findById(1L));

  }

  @Test
  void Given_NullAuthorId_When_UpdatingAuthor_Then_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> manageAuthor.update(null));
  }

  @Test
  void Given_InexistingAuthorId_When_UpdatingAuthor_Then_ThrowException() {

    Author author = new Author(1L, "Test");
    
    when(repository.existsById(1L)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> manageAuthor.update(author));

  }

  @Test
  void Given_NullAuthorId_When_DeletingAuthor_Then_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> manageAuthor.delete(null));
  }

  @Test
  void Given_InexistingAuthorId_When_DeletingAuthor_Then_ThrowException() {

    when(repository.existsById(1L)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> manageAuthor.delete(1L));

  }

}
