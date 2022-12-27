package com.nozama.api.application.dto.response.category;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import com.nozama.api.application.controller.CategoryController;

public class CategoryResponse extends EntityModel<CategoryResponse>{
	
	private Long id;
	private String name;
	private String description;
	
	public CategoryResponse() {
	}
	public CategoryResponse(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public CategoryResponse setLinks() {

        Link self = linkTo(methodOn(CategoryController.class).findById(this.getId())).withSelfRel();
        Link all = linkTo(methodOn(CategoryController.class).findAll()).withRel("authors");

        Link[] links = {
            self,
            all
        };

        this.add(links);

        return this;
    }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryResponse other = (CategoryResponse) obj;
		return Objects.equals(id, other.id);
	}

}
