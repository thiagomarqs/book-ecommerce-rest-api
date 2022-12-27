package com.nozama.api.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nozama.api.application.dto.request.book.BookActiveStatusRequest;
import com.nozama.api.application.dto.request.book.BookCreateRequest;
import com.nozama.api.application.dto.request.book.BookUpdateRequest;
import com.nozama.api.application.dto.response.author.AuthorResponse;
import com.nozama.api.application.dto.response.book.BookResponse;
import com.nozama.api.application.dto.response.category.CategoryResponse;
import com.nozama.api.application.dto.response.publisher.PublisherResponse;
import com.nozama.api.application.mapper.BookMapper;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Author;
import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.entity.Category;
import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.enums.Currency;
import com.nozama.api.domain.enums.Format;
import com.nozama.api.domain.enums.Language;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.AuthorRepository;
import com.nozama.api.domain.repository.CategoryRepository;
import com.nozama.api.domain.repository.PublisherRepository;
import com.nozama.api.domain.usecase.book.ManageBook;
import com.nozama.api.domain.usecase.book.ManageBookActiveStatus;
import com.nozama.api.domain.vo.BookDimensions;
import com.nozama.api.domain.vo.Price;

@WebMvcTest(controllers = BookController.class)
@Tag("Books")
class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ManageBook manageBookUseCase;

  @MockBean
  private ManageBookActiveStatus manageBookActiveStatusUseCase;

  @MockBean
  private EntityMapper entityMapper;

  @MockBean
  private CategoryRepository categoryRepository;

  @MockBean
  private AuthorRepository authorRepository;

  @MockBean
  private PublisherRepository publisherRepository;

  @SpyBean
  private BookMapper bookMapper;

  @Captor
  private ArgumentCaptor<ManageBook> manageBookUseCaseCaptor;

  @Captor
  private ArgumentCaptor<BookController> bookControllerCaptor;

  private BookCreateRequest createRequest;
  String createRequestBody;
  private BookResponse bookResponse;
  private BookUpdateRequest updateRequest;
  String updateRequestBody;

  @BeforeEach
  void beforeEach() throws Exception {
    createRequest = new BookCreateRequest(
      "1234567890",
      "Test", 
      "Test",
      new ArrayList<String>(),
      new Price(new BigDecimal(1), Currency.BRL), 
      Set.of(1L),
      Set.of(1L), 
      Format.DIGITAL, 
      200, 
      Language.ENGLISH,
      1L, 
      LocalDate.now(),
      "12739817239",
      new BookDimensions(10.0, 10.0, 10.0), 
      10);
    
    updateRequest = new BookUpdateRequest(
      "Test", 
      "Test",
      new ArrayList<String>(),
      new Price(new BigDecimal(1), Currency.BRL), 
      Set.of(1L),
      Set.of(1L), 
      Format.DIGITAL, 
      200, 
      Language.ENGLISH,
      1L, 
      LocalDate.now(),
      "12739817239",
      new BookDimensions(10.0, 10.0, 10.0), 
      10
    );
    
    createRequestBody = objectMapper.writeValueAsString(createRequest);
    updateRequestBody = objectMapper.writeValueAsString(updateRequest);

    bookResponse = new BookResponse(
      1L, 
      "", 
      "", 
      "", 
      new ArrayList<String>(), 
      new Price(), 
      (Set<AuthorResponse>) new HashSet<AuthorResponse>(), 
      (Set<CategoryResponse>) new HashSet<CategoryResponse>(), 
      Format.DIGITAL, 
      1,
      Language.ENGLISH, 
      new PublisherResponse(), 
      LocalDate.now(), 
      "", 
      new BookDimensions(), 
      1, 
      LocalDate.now(), 
      true 
      );

    when(entityMapper.mapEntity(manageBookUseCaseCaptor.capture(), eq(BookResponse.class))).thenReturn(bookResponse);
    when(entityMapper.mapEntity(bookControllerCaptor.capture(), eq(Book.class))).thenReturn(mock(Book.class));
  }

  @Test
  void When_CreateBook_Expect_HttpStatus201() throws Exception {
    
    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Category.class)));
    when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Author.class)));
    when(publisherRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Publisher.class)));

    mockMvc
      .perform(
        post("/api/books")
          .contentType(MediaType.APPLICATION_JSON)
          .content(createRequestBody)
      )
      .andExpect(status().isCreated());

    verify(manageBookUseCase).create(any(Book.class));
  }
  
  @Test
  void Given_InexistingCategory_When_CreatingBook_Expect_HttpStatus404NotFound() throws Exception {
    
    when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Author.class)));
    when(publisherRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Publisher.class)));
    when(categoryRepository.findById(any(Long.class))).thenThrow(new EntityNotFoundException(""));

    mockMvc
      .perform(
        post("/api/books")
          .contentType(MediaType.APPLICATION_JSON)
          .content(createRequestBody)
      )
      .andExpect(status().isNotFound());

    verifyNoInteractions(manageBookUseCase);
  }

  @Test
  void Given_InexistingAuthor_When_CreatingBook_Expect_HttpStatus404NotFound() throws Exception {
    
    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Category.class)));
    when(publisherRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Publisher.class)));
    when(authorRepository.findById(any(Long.class))).thenThrow(new EntityNotFoundException(""));

    mockMvc
      .perform(
        post("/api/books")
          .contentType(MediaType.APPLICATION_JSON)
          .content(createRequestBody)
      )
      .andExpect(status().isNotFound());

    verifyNoInteractions(manageBookUseCase);
  }

  @Test
  void Given_InexistingPublisher_When_CreatingBook_Expect_HttpStatus404NotFound() throws Exception {

    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Category.class)));
    when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Author.class)));
    when(publisherRepository.findById(any(Long.class))).thenThrow(new EntityNotFoundException(""));

    mockMvc
      .perform(
        post("/api/books")
          .contentType(MediaType.APPLICATION_JSON)
          .content(createRequestBody)
      )
      .andExpect(status().isNotFound());

    verifyNoInteractions(manageBookUseCase);
  }

  @Test
  void When_GetById_Expect_HttpStatus200OK() throws Exception {
    Long id = 1L;
   
    mockMvc.perform(
      get("/api/books/{id}", id)
    )
    .andExpect(status().isOk());
    
    verify(manageBookUseCase).findById(id);
  }

  @Test
  void When_GetAll_Expect_HttpStatus200OK() throws Exception {
    mockMvc.perform(
      get("/api/books")
    )
    .andExpect(status().isOk());

    verify(manageBookUseCase).findAll();
  }

  @Test
  void When_UpdatingBook_Expect_HttpStatus200OK() throws Exception {
    Long id = 1L;

    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Category.class)));
    when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Author.class)));
    when(publisherRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Publisher.class)));

    mockMvc.perform(
      put("/api/books/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody)
    )
    .andExpect(status().isOk());

    verify(manageBookUseCase).update(any(Book.class));
  }

  @Test
  void Given_InexistingCategory_When_UpdatingBook_Expect_HttpStatus404NotFound() throws Exception {
    Long id = 1L;

    when(categoryRepository.findById(any(Long.class))).thenThrow(new EntityNotFoundException(""));
    when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Author.class)));
    when(publisherRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Publisher.class)));

    mockMvc.perform(
      put("/api/books/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody)
    )
    .andExpect(status().isNotFound());

    verifyNoInteractions(manageBookUseCase);
  }

  @Test
  void Given_InexistingAuthor_When_UpdatingBook_Expect_HttpStatus404NotFound() throws Exception {
    Long id = 1L;

    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Category.class)));
    when(authorRepository.findById(any(Long.class))).thenThrow(new EntityNotFoundException(""));
    when(publisherRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Publisher.class)));

    mockMvc.perform(
      put("/api/books/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody)
    )
    .andExpect(status().isNotFound());

    verifyNoInteractions(manageBookUseCase);
  }

  @Test
  void Given_InexistingPublisher_When_UpdatingBook_Expect_HttpStatus404NotFound() throws Exception {
    Long id = 1L;

    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Category.class)));
    when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mock(Author.class)));
    when(publisherRepository.findById(any(Long.class))).thenThrow(new EntityNotFoundException(""));

    mockMvc.perform(
      put("/api/books/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody)
    )
    .andExpect(status().isNotFound());

    verifyNoInteractions(manageBookUseCase);
  }

  @Test
  void When_DeleteBook_Expect_HttpStatus204NoContent() throws Exception {
    Long id = 1L;

    mockMvc.perform(
      delete("/api/books/{id}", id)
    )
    .andExpect(status().isNoContent());

    verify(manageBookUseCase).delete(id);
  }

  @Test
  void Given_InexistingBook_When_DeletingBook_Expect_HttpStatus404NotFound() throws Exception {
    Long id = 1L;

    doThrow(new EntityNotFoundException("")).when(manageBookUseCase).delete(id);
    
    mockMvc.perform(
      delete("/api/books/{id}", id)
    )
    .andExpect(status().isNotFound());

    verify(manageBookUseCase).delete(id);
  }

  @Test
  void When_InactivatingBook_Expect_HttpStatus204NoContent() throws Exception {
    Long id = 1L;
    BookActiveStatusRequest request = new BookActiveStatusRequest(false);
    String body = objectMapper.writeValueAsString(request);

    mockMvc.perform(
      put("/api/books/{id}/active", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
    )
    .andExpect(status().isNoContent());

    verify(manageBookActiveStatusUseCase).setActive(id, false);
  }

  @Test
  void Given_InexistingBook_When_InactivatingBook_Expect_HttpStatus404NotFound() throws Exception {
    Long id = 1L;
    BookActiveStatusRequest request = new BookActiveStatusRequest(false);
    String body = objectMapper.writeValueAsString(request);

    doThrow(new EntityNotFoundException("")).when(manageBookActiveStatusUseCase).setActive(id, false);

    mockMvc.perform(
      put("/api/books/{id}/active", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
    )
    .andExpect(status().isNotFound());

    verify(manageBookActiveStatusUseCase).setActive(id, false);
  }

}
