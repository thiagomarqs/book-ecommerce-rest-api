package com.nozama.api.domain.usecase.book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.BookRepository;

@Component
public class ManageBook {
	
	private final BookRepository repository;

	public ManageBook(BookRepository repository) {
		this.repository = repository;
	}
	
	public Book create(Book book) {

		if(book == null) { 
			throw new InvalidEntityException("No book was informed.");
		}
		
		if(repository.existsBySku(book.getSku())) {
			throw new InvalidEntityException(String.format("A book with the sku '%s' already exists.", book.getSku()));
		}

		if(repository.existsByIsbn(book.getIsbn())) { 
			throw new InvalidEntityException(String.format("A book with the ISBN '%s' already exists.", book.getIsbn()));
		};

		book.setActive(true);
		book.setCreatedAt(LocalDate.now());
		
		return repository.save(book);
	}
	
	public Book findById(Long id) {
		if(id == null) throw new IllegalArgumentException("No id was informed.");
		
		return repository
			.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " was not found."));
	}
	
	public List<Book> findAll() {
		return repository.findAll();
	}
	
	public Book update(Book book) {
		if(book == null) throw new InvalidEntityException("No book was informed.");

		Book old = repository
			.findById(book.getId())
			.orElseThrow(() -> new EntityNotFoundException("Book with id " + book.getId() + " was not found."));
		
		if(book.getActive() == null) {
			book.setActive(old.getActive());
		}
		
		book.setSku(old.getSku());
		book.setCreatedAt(old.getCreatedAt());
		
		return repository.save(book);
	}
	
	public void delete(Long id) {
		if(id == null) throw new IllegalArgumentException("No id was informed.");
		if(!repository.existsById(id)) throw new EntityNotFoundException("Book with id " + id + " was not found.");

		repository.deleteById(id);
	}

}
