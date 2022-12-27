package com.nozama.api.application.dto.response.publisher;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import com.nozama.api.application.controller.PublisherController;

public class PublisherResponse extends EntityModel<PublisherResponse>{

	private Long id;
	private String name;
	private String site;
	
	public PublisherResponse() {}
	public PublisherResponse(Long id, String name, String site) {
		this.id = id;
		this.name = name;
		this.site = site;
	}
	
	public PublisherResponse setLinks() {

        Link self = linkTo(methodOn(PublisherController.class).findById(this.getId())).withSelfRel();
        Link all = linkTo(methodOn(PublisherController.class).findAll()).withRel("authors");

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
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
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
		PublisherResponse other = (PublisherResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
