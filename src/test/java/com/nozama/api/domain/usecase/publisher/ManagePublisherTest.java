package com.nozama.api.domain.usecase.publisher;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
public class ManagePublisherTest {

  @Mock
  private PublisherRepository repository;

  @InjectMocks
  private ManagePublisher managePublisher;

  @Test
  void Given_NullPublisher_When_CreatingPublisher_Then_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> managePublisher.create(null));
  }

  @Test
  void Given_AlreadyExistingPublisherName_When_CreatingPublisher_Then_ThrowException() {
    
    Publisher publisher = new Publisher(1L, "Test", "https://www.publisher.com", null);
        
    when(repository.existsByName("Test")).thenReturn(true);

    assertThrows(InvalidEntityException.class, () -> managePublisher.create(publisher));

  }

  @Test
  void Given_NullPublisherId_When_FindingPublisher_Then_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> managePublisher.findById(null));
  }

  @Test
  void Given_InexistingPublisherId_When_FindingById_Then_ThrowException() {

    when(repository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> managePublisher.findById(1L));

  }

  @Test
  void Given_NullPublisherId_When_UpdatingPublisher_Then_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> managePublisher.update(null));
  }

  @Test
  void Given_InexistingPublisherId_When_UpdatingPublisher_Then_ThrowException() {

    Publisher publisher = new Publisher(1L, "Test", "https://www.publisher.com", null);
    
    when(repository.existsById(1L)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> managePublisher.update(publisher));

  }

  @Test
  void Given_NullPublisherId_When_DeletingPublisher_Then_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> managePublisher.delete(null));
  }

  @Test
  void Given_InexistingPublisherId_When_DeletingPublisher_Then_ThrowException() {

    when(repository.existsById(1L)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> managePublisher.delete(1L));

  }

}
