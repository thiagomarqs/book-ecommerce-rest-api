package com.nozama.api.domain.usecase.publisher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.PublisherRepository;

@Component
public class ManagePublisher {
	
	@Autowired
	private PublisherRepository repository;
	
	public Publisher create(Publisher publisher) {
		if(publisher.equals(null)) throw new InvalidEntityException("No publisher was informed.");
		if(repository.existsByName(publisher.getName())) throw new InvalidEntityException(String.format("There's already a publisher with the name '%s'", publisher.getName()));
		
		return repository.save(publisher);
	}
	
	public Publisher findById(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		
		return repository
			.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publisher with id " + id + " was not found."));
	}
	
	public List<Publisher> findAll() {
		return repository.findAll();
	}
	
	public Publisher update(Publisher publisher) {
		if(publisher.equals(null)) throw new InvalidEntityException("No publisher was informed.");
		if(!repository.existsById(publisher.getId())) throw new EntityNotFoundException("Publisher with id " + publisher.getId() + " was not found.");
		
		return repository.save(publisher);
	}
	
	public void delete(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		if(!repository.existsById(id)) throw new EntityNotFoundException("Publisher with id " + id + " was not found.");

		repository.deleteById(id);
	}

}
