package com.nozama.api.application.dto.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import com.nozama.api.application.controller.BookController;
import com.nozama.api.domain.enums.Format;
import com.nozama.api.domain.enums.Language;
import com.nozama.api.domain.vo.BookDimensions;
import com.nozama.api.domain.vo.Price;

public class BookResponse extends EntityModel<BookResponse> {

	private Long id;
	private String sku;
	private String title;
	private String description;
	private String imageUrl;
	private Price price;
	private Set<AuthorResponse> authors;
	private Set<CategoryResponse> categories;
	private Format format;
	private Integer pages;
	private Language language;
	private PublisherResponse publisher;
	private LocalDate publishingDate;
	private String isbn;
	private BookDimensions dimensions;
	private Integer availableQuantity;
	private LocalDate createdAt;
	private Boolean active;
	
	public BookResponse() {
	}
	public BookResponse(Long id, String sku, String title, String description, String imageUrl, Price price,
			Set<AuthorResponse> authors, Set<CategoryResponse> categories, Format format, Integer pages,
			Language language, PublisherResponse publisher, LocalDate publishingDate, String isbn,
			BookDimensions dimensions, Integer availableQuantity, LocalDate createdAt, Boolean active) {
		this.id = id;
		this.sku = sku;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
		this.authors = authors;
		this.categories = categories;
		this.format = format;
		this.pages = pages;
		this.language = language;
		this.publisher = publisher;
		this.publishingDate = publishingDate;
		this.isbn = isbn;
		this.dimensions = dimensions;
		this.availableQuantity = availableQuantity;
		this.createdAt = createdAt;
		this.active = active;
	}



	public BookResponse setLinks() {

        Link self = linkTo(methodOn(BookController.class).findById(this.getId())).withSelfRel();
        Link all = linkTo(methodOn(BookController.class).findAll()).withRel("authors");

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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public Set<AuthorResponse> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<AuthorResponse> authors) {
		this.authors = authors;
	}
	public Set<CategoryResponse> getCategories() {
		return categories;
	}
	public void setCategories(Set<CategoryResponse> categories) {
		this.categories = categories;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public PublisherResponse getPublisher() {
		return publisher;
	}
	public void setPublisher(PublisherResponse publisher) {
		this.publisher = publisher;
	}
	public LocalDate getPublishingDate() {
		return publishingDate;
	}
	public void setPublishingDate(LocalDate publishingDate) {
		this.publishingDate = publishingDate;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public BookDimensions getDimensions() {
		return dimensions;
	}
	public void setDimensions(BookDimensions dimensions) {
		this.dimensions = dimensions;
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
		BookResponse other = (BookResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
