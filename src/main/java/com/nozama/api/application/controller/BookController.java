package com.nozama.api.application.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.application.dto.request.book.BookActiveStatusRequest;
import com.nozama.api.application.dto.request.book.BookCreateRequest;
import com.nozama.api.application.dto.request.book.BookUpdateRequest;
import com.nozama.api.application.dto.response.BookResponse;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Author;
import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.entity.Category;
import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.AuthorRepository;
import com.nozama.api.domain.repository.CategoryRepository;
import com.nozama.api.domain.repository.PublisherRepository;
import com.nozama.api.domain.usecase.book.ManageBook;
import com.nozama.api.domain.usecase.book.ManageBookActiveStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "Operations for managing books.")
public class BookController {

	@Autowired
	private ManageBook manageBookUseCase;

	@Autowired
	private ManageBookActiveStatus manageBookActiveStatusUseCase;

	@Autowired
  private EntityMapper entityMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Creates an book", description = "Creates an book based on the request's body payload.", tags = { "Book" })
	public ResponseEntity<BookResponse> create(@RequestBody BookCreateRequest payload) {
		
		Set<Category> categories = payload.getCategoriesId().stream()
			.map(id -> categoryRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " was not found.")))
			.collect(Collectors.toSet());

		Set<Author> authors = payload.getAuthorsId().stream()
			.map(id -> authorRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " was not found.")))
			.collect(Collectors.toSet());

		Publisher publisher = publisherRepository
			.findById(payload.getPublisherId())
			.orElseThrow(() -> new EntityNotFoundException("Publisher with id " + payload.getPublisherId() + " was not found."));

		Book book = entityMapper.mapEntity(payload, Book.class);

		book.setCategories(categories);
		book.setAuthors(authors);
		book.setPublisher(publisher);
		book.setActive(true);

		book = manageBookUseCase.create(book);

		BookResponse response = entityMapper.mapEntity(book, BookResponse.class).setLinks();

		URI uri = response.getRequiredLink(IanaLinkRelations.SELF).toUri();

		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Finds an book", description = "Finds an book by its Id.", tags = { "Book" })
	public ResponseEntity<BookResponse> findById(@PathVariable(value = "id") Long id) {
		Book found = manageBookUseCase.findById(id);
		BookResponse response = entityMapper.mapEntity(found, BookResponse.class).setLinks();

		return ResponseEntity.ok(response);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Finds all books", description = "Finds all books. If no book exists, an empty array will be returned.", tags = {
			"Book" })
	public ResponseEntity<List<BookResponse>> findAll() {
		List<Book> found = manageBookUseCase.findAll();
		List<BookResponse> response = entityMapper.mapList(found, BookResponse.class).stream().map(a -> a.setLinks())
				.toList();

		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Updates an book by its id", description = "Finds an book by the provided id and updates it. If the provided id is invalid, an exception will be thrown.", tags = {
			"Book" })
	public ResponseEntity<BookResponse> update(@PathVariable(value = "id") Long id, @RequestBody BookUpdateRequest payload) {
		
		Set<Category> categories = payload.getCategoriesId().stream()
			.map(category -> categoryRepository.findById(category)
					.orElseThrow(() -> new EntityNotFoundException("Category with id " + category + " was not found.")))
			.collect(Collectors.toSet());

		Set<Author> authors = payload.getAuthorsId().stream()
			.map(author -> authorRepository.findById(author)
					.orElseThrow(() -> new EntityNotFoundException("Author with id " + author + " was not found.")))
			.collect(Collectors.toSet());

		Publisher publisher = publisherRepository
			.findById(payload.getPublisherId())
			.orElseThrow(() -> new EntityNotFoundException("Publisher with id " + payload.getPublisherId() + " was not found."));
		
		Book book = entityMapper.mapEntity(payload, Book.class);
		
		book.setCategories(categories);
		book.setAuthors(authors);
		book.setPublisher(publisher);
		book.setId(id);

		book = manageBookUseCase.update(book);

		BookResponse response = entityMapper.mapEntity(book, BookResponse.class).setLinks();

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes an book by its id", description = "Finds an book by the provided id and deletes it. If the provided id is invalid, an exception will be thrown.", tags = {
			"Book" })
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		manageBookUseCase.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}/active")
	public ResponseEntity<?> active(@PathVariable("id") Long id, @RequestBody BookActiveStatusRequest payload) {
		Boolean active = payload.getActive();
		manageBookActiveStatusUseCase.setActive(id, active);
		return ResponseEntity.noContent().build();
	}

}
