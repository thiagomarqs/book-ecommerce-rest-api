package com.nozama.api.domain.usecase.publisher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.repository.BookRepository;
import com.nozama.api.domain.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
public class ManagePublisherActiveStatusTest {

  @Mock
  private PublisherRepository publisherRepository;

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private ManagePublisherActiveStatus managePublisherActiveStatus;

  @Test
  void When_InactivatingPublisher_Expect_PublisherAndAllItsBooksWithActiveEqualsFalse() {
    
    Publisher publisher = new Publisher(1L, "Test", "https://www.test.com", true);
    
    when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

    managePublisherActiveStatus.setActive(1L, false);

    assertFalse(() -> publisher.getActive());
  }

  @Test
  void When_ActivatingPublisher_Expect_PublisherAndAllItsBooksWithActiveEqualsTrue() {

    Publisher publisher = new Publisher(1L, "Test", "https://www.test.com", false);
    
    when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

    managePublisherActiveStatus.setActive(1L, true);

    assertTrue(() -> publisher.getActive());
  }

}
