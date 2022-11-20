package com.nozama.api.domain.usecase.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Category;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.CategoryRepository;

@Component
public class ManageCategory {
	
	@Autowired
	private CategoryRepository repository;
	
	public Category create(Category category) {
		if(category.equals(null)) throw new InvalidEntityException("No category was informed.");
		
		return repository.save(category);
	}
	
	public Category findById(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		
		return repository
			.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " was not found."));
	}
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category update(Category category) {
		if(category.equals(null)) throw new InvalidEntityException("No category was informed.");
		if(!repository.existsById(category.getId())) throw new EntityNotFoundException("Category with id " + category.getId() + " was not found.");
		
		return repository.save(category);
	}
	
	public void delete(Long id) {
		if(id.equals(null)) throw new IllegalArgumentException("No id was informed.");
		if(!repository.existsById(id)) throw new EntityNotFoundException("Category with id " + id + " was not found.");

		repository.deleteById(id);
	}

}
