package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  Boolean existsBySku(String sku);

  Boolean existsByIsbn(String isbn);
}