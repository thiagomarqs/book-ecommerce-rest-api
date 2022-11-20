package com.nozama.api.domain.usecase.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Author;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.AuthorRepository;

@Component
public class ManageAuthor {
	
	@Autowired
	private AuthorRepository repository;
	
	public Author create(Author author) {
		if(author.equals(null)) throw new InvalidEntityException("No author was informed.");
		
		return repository.save(author);
	}
	
	public Author findById(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		
		return repository
			.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " was not found."));
	}
	
	public List<Author> findAll() {
		return repository.findAll();
	}
	
	public Author update(Author author) {
		if(author.equals(null)) throw new InvalidEntityException("No author was informed.");
		if(!repository.existsById(author.getId())) throw new EntityNotFoundException("Author with id " + author.getId() + " was not found.");
		
		return repository.save(author);
	}
	
	public void delete(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		if(!repository.existsById(id)) throw new EntityNotFoundException("Author with id " + id + " was not found.");

		repository.deleteById(id);
	}

}
