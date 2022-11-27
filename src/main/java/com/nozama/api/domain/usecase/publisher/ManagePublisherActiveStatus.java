package com.nozama.api.domain.usecase.publisher;

import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.BookRepository;
import com.nozama.api.domain.repository.PublisherRepository;

@Component
public class ManagePublisherActiveStatus {
  
  private final PublisherRepository repository;

  private final BookRepository bookRepository;

  public ManagePublisherActiveStatus(PublisherRepository repository, BookRepository bookRepository) {
    this.repository = repository;
    this.bookRepository = bookRepository;
  }

  public void setActive(Long id, Boolean active) {
    
    final Publisher publisher = repository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Publisher with id " + id + " not found."));

    if(active) activate(publisher);
    else inactivate(publisher);
  }

  public void activate(Publisher publisher) {
    bookRepository.activateByPublisherId(publisher.getId());
    publisher.setActive(true);
    repository.save(publisher);
  }

  public void inactivate(Publisher publisher) {
    bookRepository.inactivateByPublisherId(publisher.getId());
    publisher.setActive(false);
    repository.save(publisher);
  }

}