package com.nozama.api.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nozama.api.application.dto.request.author.AuthorRequest;
import com.nozama.api.application.dto.response.AuthorResponse;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Author;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.usecase.author.ManageAuthor;

@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ManageAuthor manageAuthorUseCase; 

  @MockBean
  private EntityMapper entityMapper;

  @Captor
  private ArgumentCaptor<EntityMapper> entityMapperArgCaptor;

  @Test
  void When_CreateAuthor_Expect_HttpStatus201() throws Exception {
    Author authorMock = new Author(1L, "Test");
    AuthorRequest authorRequestMock = new AuthorRequest("Test");
    AuthorResponse authorResponseMock = new AuthorResponse(1L, "Test");
    String body = objectMapper.writeValueAsString(authorRequestMock);

    when(entityMapper.mapEntity(any(AuthorRequest.class), eq(Author.class))).thenReturn(authorMock);
    when(manageAuthorUseCase.create(authorMock)).thenReturn(authorMock);
    when(entityMapper.mapEntity(any(Author.class), eq(AuthorResponse.class))).thenReturn(authorResponseMock);

    mockMvc
      .perform(
        post("/api/authors")
          .contentType(MediaType.APPLICATION_JSON)
          .content(body)
      )
      .andExpect(status().isCreated());

  }

  @Test
  void When_GetAuthors_Expect_HttpStatus200() throws Exception{
    mockMvc
      .perform(get("/api/authors"))
      .andExpect(status().isOk());
  }

  @Test
  void When_GetAuthorById_Expect_HttpStatus200() throws Exception{
    Author mock = new Author(1L, "Mock");
    
    when(manageAuthorUseCase.findById(1L)).thenReturn(mock);
    when(entityMapper.mapEntity(mock, AuthorResponse.class)).thenReturn(new AuthorResponse(1L, "Mock"));

    mockMvc
      .perform(get("/api/authors/{id}", 1L))
      .andExpect(status().isOk());
  }

  @Test
  void Given_InexistingAuthorId_When_GetAuthorById_Expect_HttpStatus404() throws Exception{
    
    when(manageAuthorUseCase.findById(1L)).thenThrow(EntityNotFoundException.class);

    mockMvc
      .perform(get("/api/authors/{id}", 1L))
      .andExpect(status().isNotFound());
  }

  @Test
  void When_Update_Expect_HttpStatus200() throws Exception {
    AuthorRequest requestMock = new AuthorRequest("Mock");
    String requestMockJson = objectMapper.writeValueAsString(requestMock);
    Author authorMock = new Author(1L, "Mock");
    AuthorResponse responseMock = new AuthorResponse(1L, "Mock");

    when(entityMapper.mapEntity(any(AuthorRequest.class), eq(Author.class))).thenReturn(authorMock);
    when(manageAuthorUseCase.update(any(Author.class))).thenReturn(authorMock);
    when(entityMapper.mapEntity(any(Author.class), eq(AuthorResponse.class))).thenReturn(responseMock);

    mockMvc
      .perform(
        put("/api/authors/{id}", 1L)
          .contentType(MediaType.APPLICATION_JSON)
          .content(requestMockJson)
      )
      .andExpect(status().isOk());
  }

  @Test
  void Given_InexistingAuthorId_When_DeleteAuthor_Expect_HttpStatus404() throws Exception {
    var id = 1L;

    doThrow(new EntityNotFoundException("")).when(manageAuthorUseCase).delete(id);

    mockMvc
      .perform(delete("/api/authors/{id}", id))
      .andExpect(status().isNotFound());
  }

  @Test
  void Given_InvalidIdReceived_When_DeleteAuthor_Expect_HttpStatus400() throws Exception {
    Long invalidIdNull = null;
    String invalidIdString = "";
    Boolean invalidIdBoolean = false;

    doThrow(new EntityNotFoundException("")).when(manageAuthorUseCase).delete(any());

    mockMvc
      .perform(delete("/api/authors/{id}", invalidIdString))
      .andExpect(status().isBadRequest());
    
    mockMvc
      .perform(delete("/api/authors/{id}", invalidIdBoolean))
      .andExpect(status().isBadRequest());
    
    mockMvc
      .perform(delete("/api/authors/{id}", invalidIdNull))
      .andExpect(status().isBadRequest());
  }

}
