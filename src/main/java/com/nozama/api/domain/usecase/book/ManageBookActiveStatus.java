package com.nozama.api.domain.usecase.book;

import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.BookRepository;

@Component
public class ManageBookActiveStatus {
  
  private final BookRepository repository;

  public ManageBookActiveStatus(BookRepository repository) {
    this.repository = repository;
  }

  public void setActive(Long id, Boolean active) {
    
    final Book book = repository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found."));

    if(active) activate(book);
    else inactivate(book);
  }

  private void activate(Book book) {
    book.setActive(true);
    repository.save(book);
  }

  private void inactivate(Book book) {
    book.setActive(false);
    repository.save(book);
  }

}
