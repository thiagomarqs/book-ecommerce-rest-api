package com.nozama.api.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

@Entity
public class Publisher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="The publisher's name is required.")
	private String name;
	
	@URL(message="The informed publisher's site is invalid.")
	private String site;
	
	@OneToMany(mappedBy="publisher", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Book> books = new ArrayList<>();

	private Boolean active;
	
	public Publisher() {}
	public Publisher(Long id, String name, String site, Boolean active) {
		this.id = id;
		this.name = name;
		this.site = site;
		this.active = active;
	}

	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
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
	public List<Book> getBooks() {
		return this.books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
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
		Publisher other = (Publisher) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
