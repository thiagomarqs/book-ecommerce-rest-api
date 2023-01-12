package com.nozama.api.application.dto.response.author;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import com.nozama.api.application.controller.AuthorController;

public class AuthorResponse extends EntityModel<AuthorResponse> {

    private Long id;
    private String name;

    public AuthorResponse() {}
    public AuthorResponse(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public AuthorResponse setLinks() {

        Link self = linkTo(methodOn(AuthorController.class).findById(this.getId())).withSelfRel();
        Link all = linkTo(methodOn(AuthorController.class).findAll()).withRel("authors");

        Link[] links = {
            self,
            all
        };

        this.add(links);

        return this;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorResponse other = (AuthorResponse) obj;
		return Objects.equals(id, other.id);
	}
    
    
}
