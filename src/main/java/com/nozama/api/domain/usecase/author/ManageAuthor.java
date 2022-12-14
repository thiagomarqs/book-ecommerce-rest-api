package com.nozama.api.domain.usecase.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Author;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidArgumentException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.AuthorRepository;

@Component
public class ManageAuthor {
	
	private final AuthorRepository repository;
	
	@Autowired
	public ManageAuthor(AuthorRepository repository) {
		this.repository = repository;
	}

	public Author create(Author author) {
		if(author == null) throw new InvalidEntityException("No author was informed.");
		if(repository.existsByName(author.getName())) throw new InvalidEntityException(String.format("There's already an author with the name '%s'.", author.getName()));

		return repository.save(author);
	}
	
	public Author findById(Long id) {
		if(id == null) throw new IllegalArgumentException("No id was informed.");
		
		return repository
			.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " was not found."));
	}
	
	public List<Author> findAll() {
		return repository.findAll();
	}
	
	public Author update(Author author) {
		if(author == null) throw new InvalidEntityException("No author was informed.");
		if(!repository.existsById(author.getId())) throw new EntityNotFoundException("Author with id " + author.getId() + " was not found.");
		
		return repository.save(author);
	}
	
	public void delete(Long id) {
		if(id == null) throw new InvalidArgumentException("No id was informed.");
		if(!repository.existsById(id)) throw new EntityNotFoundException("Author with id " + id + " was not found.");

		repository.deleteById(id);
	}

}
