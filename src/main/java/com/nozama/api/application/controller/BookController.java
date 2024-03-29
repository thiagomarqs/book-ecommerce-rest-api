package com.nozama.api.application.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
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
import com.nozama.api.application.dto.response.book.BookResponse;
import com.nozama.api.application.mapper.BookMapper;
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
import io.swagger.v3.oas.annotations.Parameter;
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
	
	@Autowired
	private BookMapper bookMapper;

	private Link[] links = {
		Link.of("/api/books"),
		Link.of("/api/books/{id}", "book")
	};

	@PostConstruct
	private void initialize() {
		bookMapper
			.getMapper()
			.createTypeMap(BookCreateRequest.class, Book.class)
			.addMappings(mapper -> mapper.skip(Book::setId));
	}

	@PostMapping(
		consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	@Operation(
		summary = "Creates an book", 
		description = "Creates an book based on the request body payload.", 
		tags = { "Admin", "Book" }
	)
	public ResponseEntity<BookResponse> create(@RequestBody @Parameter(description = "The new book.") BookCreateRequest payload) {
		
		Set<Category> categories = payload.getCategoriesId()
			.stream()
			.map(id -> categoryRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " was not found.")))
			.collect(Collectors.toSet());

		Set<Author> authors = payload.getAuthorsId()
			.stream()
			.map(id -> authorRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " was not found.")))
			.collect(Collectors.toSet());

		Publisher publisher = publisherRepository
			.findById(payload.getPublisherId())
			.orElseThrow(() -> new EntityNotFoundException("Publisher with id " + payload.getPublisherId() + " was not found."));

		Book book = bookMapper.toBook(payload);

		book.setCategories(categories);
		book.setAuthors(authors);
		book.setPublisher(publisher);

		book = manageBookUseCase.create(book);

		BookResponse response = entityMapper.mapEntity(book, BookResponse.class).setLinks();

		URI uri = response.getRequiredLink(IanaLinkRelations.SELF).toUri();

		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping(
		value = "/{id}", 
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	@Operation(
		summary = "Finds an book", 
		description = "Finds an book by its Id.", 
		tags = { "Book" }
	)
	public ResponseEntity<BookResponse> findById(@PathVariable(value = "id") @Parameter(description = "The id of the book.") Long id) {
		Book found = manageBookUseCase.findById(id);
		BookResponse response = entityMapper.mapEntity(found, BookResponse.class).setLinks();

		return ResponseEntity.ok(response);
	}

	@GetMapping(
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	@Operation(
		summary = "Finds all books", 
		description = "Finds all books. If no book exists, an empty array will be returned.", 
		tags = { "Book" }
	)
	public ResponseEntity<CollectionModel<BookResponse>> findAll() {
		List<Book> found = manageBookUseCase.findAll();
		List<BookResponse> list = entityMapper.mapList(found, BookResponse.class);

		var response = CollectionModel.of(list, links);

		return ResponseEntity.ok(response);
	}

	@PutMapping(
		value = "/{id}", 
		consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	@Operation(
		summary = "Updates an book by its id", 
		description = "Finds an book by the provided id and updates it. If the provided id is invalid, an exception will be thrown.", 
		tags = { "Admin", "Book" }
	)
	public ResponseEntity<BookResponse> update(@PathVariable(value = "id") @Parameter(description = "The id of the book.") Long id, @RequestBody @Parameter(description = "The new information of the book.") BookUpdateRequest payload) {
		
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
	@Operation(
		summary = "Deletes an book by its id", 
		description = "Finds an book by the provided id and deletes it. If the provided id is invalid, an exception will be thrown.", 
		tags = { "Admin", "Book" }
	)
	public ResponseEntity<?> delete(@PathVariable(value = "id") @Parameter(description = "The id of the book.") Long id) {
		manageBookUseCase.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}/active")
	@Operation(
		summary = "Changes the active status of a book", 
		description = "Given a valid book id, changes the active status of it with the new one informed in the request body.", 
		tags = { "Admin", "Book" }
	)
	public ResponseEntity<?> active(@PathVariable("id") @Parameter(description = "The id of the book.") Long id, @RequestBody @Parameter(description = "The new active status.") BookActiveStatusRequest payload) {
		Boolean active = payload.getActive();
		manageBookActiveStatusUseCase.setActive(id, active);
		return ResponseEntity.noContent().build();
	}

}
