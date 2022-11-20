package com.nozama.api.domain.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.ISBN.Type;
import org.hibernate.validator.constraints.URL;

import com.nozama.api.domain.enums.Format;
import com.nozama.api.domain.enums.Language;
import com.nozama.api.domain.vo.BookDimensions;
import com.nozama.api.domain.vo.Price;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=10, max=10, message = "SKU's length must be of 10 characters.")
	@NotBlank(message="The book's SKU is required.")
	private String sku;
	
	@Size(max=255, message="The book's title must not be longer than 255 characters.")
	@NotBlank(message="The book's title is required.")
	private String title;
	
	@Size(max=1000, message="The book's description must not be longer than 1000 characters.")
	@NotBlank(message="The book's description is required.")
	private String description;
	
	@Size(max=1000, message="The book's image URL must not be longer than 1000 characters.")
	@URL(message="The book's image URL is not valid.")
	@NotBlank(message="The book's image URL is required.")
	private String imageUrl;
	
	@NotNull(message="The book's price is required.")
	@Embedded
	@Valid
	private Price price;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "book_author",
		joinColumns = { @JoinColumn(name = "book_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "author_id")}
	)
	private Set<Author> authors = new HashSet<>();
	
	@ManyToMany
	@JoinTable(
		name = "book_category",
		joinColumns = { @JoinColumn(name = "book_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "category_id") }
	)
	private Set<Category> categories = new HashSet<>();
	
	@NotNull(message="The books's format is required.")
	@Enumerated(EnumType.STRING)
	private Format format;
	
	@Min(value = 1, message="A valid number of pages is required.")
	@Digits(integer=5, fraction=0, message="A valid number of pages is required.")
	@NotNull(message="The books's number of pages is required.")
	private Integer pages;
	
	@NotNull(message="The book's language is required.")
	@Enumerated(EnumType.STRING)
	private Language language;
	
	@NotNull(message="The book's publisher is required.")
	@ManyToOne
	private Publisher publisher;
	
	@NotNull(message="The book's publishing date is required.")
	@PastOrPresent(message="The book's publishing date must not be a future date.")
	private LocalDate publishingDate;
	
	@ISBN(type = Type.ISBN_13, message="The informed ISBN does not follow the ISBN-13 format.")
	@NotBlank(message="The books's ISBN is required.")
	private String isbn;
	
	@NotNull(message="The book's dimensions are required.")
	@Embedded
	private BookDimensions dimensions;
	
	@Min(value = 1, message="The informed quantity is not valid.")
	@Digits(integer=6, fraction=0, message="The informed quantity is not valid.")
	@NotNull(message="The books's available quantity is required.")
	private Integer availableQuantity;
	
	@NotNull(message="The book's creation date is required.")
	@PastOrPresent(message="The book's creation date must not be a future date.")
	private LocalDate createdAt;
	
	@NotNull(message="The book's active status is required.")
	private Boolean active;
	
	public Book() {}
	public Book(Long id, String sku, String title, String description, String imageUrl, Price price, Format format,
			Integer pages, Language language, Publisher publisher, LocalDate publishingDate, String isbn,
			BookDimensions dimensions, Integer availableQuantity, LocalDate createdAt, Boolean active) {
		this.id = id;
		this.sku = sku;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
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
	public Set<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
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
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
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
	public BookDimensions getDimensions() {
		return dimensions;
	}
	public void setDimensions(BookDimensions dimensions) {
		this.dimensions = dimensions;
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
		Book other = (Book) obj;
		return Objects.equals(id, other.id);
	}
		
}
