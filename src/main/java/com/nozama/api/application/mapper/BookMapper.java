package com.nozama.api.application.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Book;

@Component
public class BookMapper {
  
  private final ModelMapper mapper = new ModelMapper();

  public <Dto> Book toBook(Dto source) {
    return mapper.map(source, Book.class);
  }

  public <Dto> List<Book> toBookList(List<Dto> source) {
    if (source.size() == 0) return List.of();

    return source
        .stream()
        .map(s -> this.toBook(s))
        .toList();
  }

  public ModelMapper getMapper() {
    return this.mapper;
  }

}
