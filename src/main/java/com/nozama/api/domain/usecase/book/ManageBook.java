package com.nozama.api.domain.usecase.book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.BookRepository;

@Component
public class ManageBook {
	
	@Autowired
	private BookRepository repository;
	
	public Book create(Book book) {
		if(book.equals(null)) throw new InvalidEntityException("No book was informed.");	
		
		book.setCreatedAt(LocalDate.now());
		
		return repository.save(book);
	}
	
	public Book findById(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		
		return repository
			.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " was not found."));
	}
	
	public List<Book> findAll() {
		return repository.findAll();
	}
	
	public Book update(Book book) {
		if(book.equals(null)) throw new InvalidEntityException("No book was informed.");

		Book old = repository
			.findById(book.getId())
			.orElseThrow(() -> new EntityNotFoundException("Book with id " + book.getId() + " was not found."));
		
		if(book.getActive() == null) {
			book.setActive(old.getActive());
		}
		
		book.setCreatedAt(old.getCreatedAt());
		
		return repository.save(book);
	}
	
	public void delete(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		if(!repository.existsById(id)) throw new EntityNotFoundException("Book with id " + id + " was not found.");

		repository.deleteById(id);
	}

}
