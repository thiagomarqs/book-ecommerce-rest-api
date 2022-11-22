package com.nozama.api.domain.usecase.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.BookRepository;

@Component
public class ManageBookActiveStatus {
  
  @Autowired
  private BookRepository repository;

  public void setActive(Long id, Boolean active) {
    
    final Book book = repository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found."));

    if(active) activate(book);
    else inactivate(book);
  }

  public void activate(Book book) {
    book.setActive(true);
    repository.save(book);
  }

  public void inactivate(Book book) {
    book.setActive(false);
    repository.save(book);
  }

}
