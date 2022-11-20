package com.nozama.api.application.dto.request;

import java.time.LocalDate;
import java.util.Set;

import com.nozama.api.domain.enums.Format;
import com.nozama.api.domain.enums.Language;
import com.nozama.api.domain.vo.BookDimensions;
import com.nozama.api.domain.vo.Price;

public class BookRequest {

	private String sku;
	private String title;
	private String description;
	private String imageUrl;
	private Price price;
	private Set<Long> authorsId;
	private Set<Long> categoriesId;
	private Format format;
	private Integer pages;
	private Language language;
	private Long publisherId;
	private LocalDate publishingDate;
	private String isbn;
	private BookDimensions dimensions;
	private Integer availableQuantity;
	
	public BookRequest() {
	}
	public BookRequest(String sku, String title, String description, String imageUrl, Price price,
			Set<Long> authorsId, Set<Long> categoriesId, Format format, Integer pages, Language language,
			Long publisherId, LocalDate publishingDate, String isbn, BookDimensions dimensions,
			Integer availableQuantity) {
		this.sku = sku;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
		this.authorsId = authorsId;
		this.categoriesId = categoriesId;
		this.format = format;
		this.pages = pages;
		this.language = language;
		this.publisherId = publisherId;
		this.publishingDate = publishingDate;
		this.isbn = isbn;
		this.dimensions = dimensions;
		this.availableQuantity = availableQuantity;
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

	public Set<Long> getAuthorsId() {
		return authorsId;
	}

	public void setAuthorsId(Set<Long> authorsId) {
		this.authorsId = authorsId;
	}

	public Set<Long> getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(Set<Long> categoriesId) {
		this.categoriesId = categoriesId;
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

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
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


}
